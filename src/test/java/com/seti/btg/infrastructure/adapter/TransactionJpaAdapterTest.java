package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.domain.model.enumerator.TransactionType;
import com.seti.btg.domain.repository.NotificationRepositoryPort;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.TransactionDbMapper;
import com.seti.btg.infrastructure.adapter.repository.CustomerRepository;
import com.seti.btg.infrastructure.adapter.repository.FundRepository;
import com.seti.btg.infrastructure.adapter.repository.SubscriptionRepository;
import com.seti.btg.infrastructure.adapter.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionJpaAdapterTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    @Qualifier("emailNotification")
    private NotificationRepositoryPort emailRepositoryPort;

    @Mock
    @Qualifier("emailNotification")
    private NotificationRepositoryPort smsRepositoryPort;
    @Mock
    private FundRepository fundRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private TransactionDbMapper transactionDbMapper;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TransactionJpaAdapter transactionJpaAdapter;

    private Transaction testTransaction;
    private TransactionEntity testTransactionEntity;
    private CustomerEntity testCustomer;
    private FundEntity testFund;
    private String testMessage;
    @Mock
    private TypedQuery<TransactionEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<TransactionEntity> transactions = new ArrayList<>();
        HashSet<FundEntity> funds = new HashSet<>();
        HashSet<CustomerEntity> customers = new HashSet<>();


        testCustomer = new CustomerEntity(1L, "name", "email", "3214123232", BigDecimal.valueOf(40000.00), NotificationType.EMAIL, transactions, funds);
        testFund = new FundEntity(1L, "fondo1", BigDecimal.valueOf(40000.00), "FIC", transactions, customers);

        testTransaction = new Transaction();
        testTransaction.setCustomer(new Customer(1L, "name", "email", "3214123232", BigDecimal.valueOf(40000.00), NotificationType.EMAIL));
        testTransaction.setFund(new Fund(1L, "fondo1", BigDecimal.valueOf(40000.00), "FIC"));
        testTransaction.setAmount(new BigDecimal("500"));

        testMessage = "Test notification message";
        CustomerEntity customer = new CustomerEntity(1L, "name", "email", "3214123232", BigDecimal.valueOf(40000.00), NotificationType.SMS, null, null);
        FundEntity fund = new FundEntity(1L, "fondo1", BigDecimal.valueOf(40000.00), "FIC", null, null);
        UUID uuid = UUID.randomUUID();
        testTransactionEntity = new TransactionEntity(uuid, customer, fund, TransactionType.APERTURA, LocalDate.now(), BigDecimal.valueOf(40000.00));
    }

    @Test
    void testGetAll() {
        CustomerEntity customer = new CustomerEntity(1L, "name", "email", "3214123232", BigDecimal.valueOf(40000.00), NotificationType.EMAIL, null, null);
        CustomerEntity customer2 = new CustomerEntity(2L, "name", "email", "3213808302", BigDecimal.valueOf(40000.00), NotificationType.SMS, null, null);
        FundEntity fund = new FundEntity(1L, "fondo1", BigDecimal.valueOf(40000.00), "FIC", null, null);
        FundEntity fund2 = new FundEntity(2L, "fondo2", BigDecimal.valueOf(40000.00), "FIC", null, null);
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        // Arrange
        TransactionEntity entity1 = new TransactionEntity(uuid, customer, fund, TransactionType.APERTURA, LocalDate.now(), BigDecimal.valueOf(40000.00));
        TransactionEntity entity2 = new TransactionEntity(uuid2, customer2, fund2, TransactionType.CANCELACION, LocalDate.now(), BigDecimal.valueOf(40000.00));
        List<TransactionEntity> entityList = Arrays.asList(entity1, entity2);

        UUID uuid3 = UUID.randomUUID();
        Customer customer3 = new Customer(3L, "name", "email", "3144291482", BigDecimal.valueOf(40000.00), NotificationType.EMAIL);
        Fund fund3 = new Fund(3L, "fondo3", BigDecimal.valueOf(40000.00), "FIC");
        Customer customer4 = new Customer(4L, "name", "email", "3133031741", BigDecimal.valueOf(40000.00), NotificationType.EMAIL);
        Fund fund4 = new Fund(4L, "fondo4", BigDecimal.valueOf(40000.00), "FIC");
        UUID uuid4 = UUID.randomUUID();

        Transaction domain1 = new Transaction(uuid3, customer3, fund3, TransactionType.APERTURA, LocalDate.now(), BigDecimal.valueOf(40000.00));
        Transaction domain2 = new Transaction(uuid4, customer4, fund4, TransactionType.APERTURA, LocalDate.now(), BigDecimal.valueOf(40000.00));

        Mockito.when(transactionRepository.findAll()).thenReturn(entityList);
        Mockito.when(transactionDbMapper.toDomain(entity1)).thenReturn(domain1);
        Mockito.when(transactionDbMapper.toDomain(entity2)).thenReturn(domain2);

        // Act
        List<Transaction> result = transactionJpaAdapter.getAll();

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(domain1, result.get(0));
        Assertions.assertEquals(domain2, result.get(1));
    }

    @Test
    void testGetAllEmptyList() {
        // Arrange
        Mockito.when(transactionRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Transaction> result = transactionJpaAdapter.getAll();

        // Assert
        Assertions.assertEquals(0, result.size());
    }

    /*@Test
    void testCreateNewTransaction_Success() {
        List<TransactionEntity> transactions = new ArrayList<>();
        HashSet<FundEntity> funds = new HashSet<>();
        CustomerEntity custo1 = new CustomerEntity(1L, "name", "email", "3214123232", BigDecimal.valueOf(40000.00), NotificationType.EMAIL, transactions, funds);
        FundEntity fund1 = new FundEntity(1L, "fondo1", BigDecimal.valueOf(40000.00), "FIC", null, null);
        Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.of(testCustomer));
        Mockito.when(fundRepository.findById(2L)).thenReturn(Optional.of(testFund));
        Mockito.when(transactionJpaAdapter.isCustomerSubscribedToFund(custo1, fund1)).thenReturn(false);

        TransactionEntity savedTransactionEntity = new TransactionEntity();
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(savedTransactionEntity);

        Transaction savedTransaction = new Transaction();
        Mockito.when(transactionDbMapper.toDomain(savedTransactionEntity)).thenReturn(savedTransaction);

        Transaction result = transactionJpaAdapter.createNewTransaction(testTransaction);

        Assertions.assertNotNull(result);
        Mockito.verify(customerRepository).save(testCustomer);
        Mockito.verify(subscriptionRepository).save(Mockito.any(SubscriptionEntity.class));
        Mockito.verify(transactionRepository).save(Mockito.any(TransactionEntity.class));
        Assertions.assertEquals(new BigDecimal("500"), testCustomer.getBalance());
    }*/

    @Test
    void testCreateNewTransaction_CustomerNotFound() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        ErrorException exception = Assertions.assertThrows(ErrorException.class,
                () -> transactionJpaAdapter.createNewTransaction(testTransaction));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        Assertions.assertEquals(ErrorConstant.CUSTOMER_NOT_FOUND, exception.getErrorMessage());
    }

    @Test
    void testGetTransactionsByUser_WithTransactions() {
        // Arrange
        Long userId = 1L;
        TransactionEntity entity1 = new TransactionEntity();
        TransactionEntity entity2 = new TransactionEntity();
        List<TransactionEntity> entityList = Arrays.asList(entity1, entity2);

        Transaction domain1 = new Transaction();
        Transaction domain2 = new Transaction();

        Mockito.when(entityManager.createQuery(Mockito.anyString(), eq(TransactionEntity.class))).thenReturn(typedQuery);
        Mockito.when(typedQuery.setParameter("customerId", userId)).thenReturn(typedQuery);
        Mockito.when(typedQuery.getResultList()).thenReturn(entityList);
        Mockito.when(transactionDbMapper.toDomain(entity1)).thenReturn(domain1);
        Mockito.when(transactionDbMapper.toDomain(entity2)).thenReturn(domain2);

        // Act
        List<Transaction> result = transactionJpaAdapter.getTransactionsByUser(userId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(domain1, result.get(0));
        Assertions.assertEquals(domain2, result.get(1));

        Mockito.verify(entityManager).createQuery(
                "SELECT t FROM TransactionEntity t WHERE t.customer.id = :customerId ORDER BY t.transactionDate DESC",
                TransactionEntity.class
        );
        Mockito.verify(typedQuery).setParameter("customerId", userId);
        Mockito.verify(typedQuery).getResultList();
        Mockito.verify(transactionDbMapper, times(2)).toDomain(any(TransactionEntity.class));
    }

    @Test
    void testIsCustomerSubscribedToFund_WithNullSubscribedFunds() {
        // Arrange
        testCustomer.setSubscribedFunds(null);

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionJpaAdapter.isCustomerSubscribedToFund(testCustomer, testFund));
    }

    @Test
    void testIsCustomerSubscribedToFund_Success() {
        //Arrange
        FundEntity fund1 = new FundEntity(1L, "fondo1", BigDecimal.valueOf(10000.00), "FIC", null, null);
        FundEntity fund2 = new FundEntity(2L, "fondo2", BigDecimal.valueOf(5000.00), "FIC", null, null);


        HashSet<FundEntity> subscribedFunds = new HashSet<>();
        subscribedFunds.add(fund1);


        CustomerEntity customer = new CustomerEntity(1L, "name", "email", "3214123232", BigDecimal.valueOf(40000.00), NotificationType.EMAIL, new ArrayList<>(), subscribedFunds);


        boolean isSubscribed = transactionJpaAdapter.isCustomerSubscribedToFund(customer, fund1);  // Se espera que devuelva true

        // Assert
        Assertions.assertTrue(isSubscribed, "El cliente debería estar suscrito al fondo");


        isSubscribed = transactionJpaAdapter.isCustomerSubscribedToFund(customer, fund2);  // Se espera que devuelva false
        Assertions.assertFalse(isSubscribed, "El cliente no debería estar suscrito a este fondo");
    }

    @Test
    void testSendNotification_EmailNotification() {
        // Arrange
        testCustomer.setNotificationType(NotificationType.EMAIL);

        // Act
        transactionJpaAdapter.sendNotification(testCustomer, testFund, testTransactionEntity, testMessage);

        // Assert
        verify(emailRepositoryPort).sendNotification(testCustomer, testFund, testTransactionEntity, testMessage);
        verify(smsRepositoryPort, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void testSendNotification_SmsNotification() {

        // Arrange
        testCustomer.setNotificationType(NotificationType.SMS);

        // Act
        transactionJpaAdapter.sendNotification(testCustomer, testFund, testTransactionEntity, testMessage);

        // Assert
        verify(smsRepositoryPort).sendNotification(testCustomer, testFund, testTransactionEntity, testMessage);
        verify(emailRepositoryPort, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void testSendNotification_NullNotificationType() {
        var testMessage = "Test notification message";
        // Arrange
        testCustomer.setNotificationType(null);

        // Act
        transactionJpaAdapter.sendNotification(testCustomer, testFund, testTransactionEntity, testMessage);

        // Assert
        verify(emailRepositoryPort, never()).sendNotification(any(), any(), any(), any());
        verify(smsRepositoryPort, never()).sendNotification(any(), any(), any(), any());
    }

}
