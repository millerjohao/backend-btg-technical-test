package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.constant.ResponseConstant;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.domain.model.enumerator.TransactionType;
import com.seti.btg.domain.repository.NotificationRepositoryPort;
import com.seti.btg.domain.repository.TransactionRepositoryPort;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.SubscriptionEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.TransactionDbMapper;
import com.seti.btg.infrastructure.adapter.repository.CustomerRepository;
import com.seti.btg.infrastructure.adapter.repository.FundRepository;
import com.seti.btg.infrastructure.adapter.repository.SubscriptionRepository;
import com.seti.btg.infrastructure.adapter.repository.TransactionRepository;
import com.seti.btg.infrastructure.adapter.serializable.SubscriptionId;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionJpaAdapter implements TransactionRepositoryPort {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    @Qualifier("emailNotification")
    private NotificationRepositoryPort emailRepositoryPort;
    @Autowired
    @Qualifier("smsNotification")
    private NotificationRepositoryPort smsRepositoryPort;
    @Autowired
    private TransactionDbMapper transactionDbMapper;
    @Autowired
    private EntityManager entityManager;

    /**
     * Crea una transacción, a su vez de que realiza la subscripción en la tabla intermedia y envía la notificación
     *
     * @param transaction
     * @return Objeto transacción
     */
    @Override
    public Transaction createNewTransaction(Transaction transaction) {
        // Verificar si el cliente existe
        var customer = customerRepository.findById(transaction.getCustomer().getId()).orElseThrow(() -> new ErrorException(HttpStatus.NOT_FOUND, ErrorConstant.CUSTOMER_NOT_FOUND));

        // Verificar si el fondo existe
        var fund = fundRepository.findById(transaction.getFund().getId()).orElseThrow(() -> new ErrorException(HttpStatus.NOT_FOUND, String.format(ErrorConstant.FUND_NOT_FOUND, transaction.getFund().getId())));

        // Verificar si el cliente ya está afiliado al fondo
        if (isCustomerSubscribedToFund(customer, fund)) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorConstant.USER_ALREADY_IN_FUND);
        }

        // Verificar si se cumple monto mínimo del fondo
        if (fund.getMinAmount().compareTo(transaction.getAmount()) > 0) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorConstant.MIN_AMOUNT_NO_REACHED);
        }
        // Verificar si el cliente tiene saldo suficiente para afiliarse
        if (customer.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, String.format(ErrorConstant.UNFUNDED_SUBSCRIPTION, fund.getName()));
        }

        // Descontar el monto del saldo del cliente
        customer.setBalance(customer.getBalance().subtract(transaction.getAmount()));
        customerRepository.save(customer);

        // Creación del objeto subscripçión
        SubscriptionEntity subscription = new SubscriptionEntity();
        SubscriptionId subscriptionId = new SubscriptionId();
        subscriptionId.setIdCustomer(customer.getId());
        subscriptionId.setIdFund(fund.getId());

        subscription.setId(subscriptionId);
        subscription.setCustomer(customer);
        subscription.setFund(fund);
        subscription.setAmount(transaction.getAmount());

        // Guardar la subscripción
        subscriptionRepository.save(subscription);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCustomer(customer);
        transactionEntity.setFund(fund);
        transactionEntity.setTransactionType(TransactionType.APERTURA);
        transactionEntity.setTransactionDate(LocalDate.now());
        transactionEntity.setAmount(transaction.getAmount());

        // Guardar la transacción
        TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);

        String message = String.format(ResponseConstant.SUCCESSFULLY_SUBSCRIPTION_, fund.getName());
        sendNotification(customer, fund, transactionEntity, message);

        return transactionDbMapper.toDomain(savedTransaction);
    }

    /**
     * Metodo para listar todas las transacciones sin importar el cliente
     *
     * @return Objeto lista de transacciones
     */
    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll().stream().map(transactionDbMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * @param id del usuario
     * @return Retorna objeto con todas las transacciones tipo historico de un cliente
     */
    @Override
    public List<Transaction> getTransactionsByUser(Long id) {
        String query = "SELECT t FROM TransactionEntity t WHERE t.customer.id = :customerId ORDER BY t.transactionDate DESC";
        List<TransactionEntity> entities = entityManager.createQuery(query, TransactionEntity.class).setParameter("customerId", id).getResultList();

        return entities.stream().map(transactionDbMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * Cancela la subscripción de un cliente al fondo
     *
     * @param customerId
     * @param fundId
     * @return Objeto tipo transacción
     */
    @Override
    public Transaction cancelTransaction(Long customerId, Long fundId) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new ErrorException(HttpStatus.NOT_FOUND, ErrorConstant.CUSTOMER_NOT_FOUND));

        var fund = fundRepository.findById(fundId).orElseThrow(() -> new ErrorException(HttpStatus.NOT_FOUND, String.format(ErrorConstant.FUND_NOT_FOUND, fundId)));

        // Validar si el cliente está vinculado al fondo
        if (!isCustomerSubscribedToFund(customer, fund)) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorConstant.CUSTOMER_NOT_IN_FUND);
        }

        customer.getSubscribedFunds().remove(fund);
        customerRepository.save(customer);

        // Devolver el monto de vinculación al saldo del cliente
        customer.setBalance(customer.getBalance().add(fund.getMinAmount()));
        customerRepository.save(customer);

        // Registrar la transacción de cancelación
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCustomer(customer);
        transactionEntity.setFund(fund);
        transactionEntity.setTransactionType(TransactionType.CANCELACION);
        transactionEntity.setTransactionDate(LocalDate.now());
        transactionEntity.setAmount(fund.getMinAmount());

        // Guardar la transacción
        TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);

        return transactionDbMapper.toDomain(savedTransaction);
    }

    /**
     * @param customer Objeto cliente
     * @param fund     Objeto fondo
     * @return Revisa si el cliente está asociado al fondo y retorna un true/false
     */
    public boolean isCustomerSubscribedToFund(CustomerEntity customer, FundEntity fund) {
        return customer.getSubscribedFunds().contains(fund);
    }

    /**
     * @param customer    Objeto cliente
     * @param fund        Objeto fondo
     * @param transaction Objeto transacción
     * @param message     mensaje en el cuerpo de la notificación
     */
    public void sendNotification(CustomerEntity customer, FundEntity fund, TransactionEntity transaction, String message) {
        if (customer.getNotificationType() == NotificationType.EMAIL) {
            emailRepositoryPort.sendNotification(customer, fund, transaction, message);
        } else if (customer.getNotificationType() == NotificationType.SMS) {
            smsRepositoryPort.sendNotification(customer, fund, transaction, message);
        }
    }
}
