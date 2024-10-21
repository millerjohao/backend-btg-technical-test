package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.enumerator.TransactionType;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDbMapperImplTest {

    private TransactionDbMapperImpl transactionDbMapper;
    private Customer mockCustomer;
    private Fund mockFund;
    private Transaction mockTransaction;
    private TransactionEntity mockTransactionEntity;

    private CustomerEntity mockCustomerEntity;
    private FundEntity mockFundEntity;


    @BeforeEach
    public void setUp() {
        transactionDbMapper = new TransactionDbMapperImpl();

        mockCustomer = mock(Customer.class);
        mockFund = mock(Fund.class);
        mockTransaction = mock(Transaction.class);
        mockTransactionEntity = mock(TransactionEntity.class);

        mockTransactionEntity = mock(TransactionEntity.class);
        mockCustomerEntity = mock(CustomerEntity.class);
        mockFundEntity = mock(FundEntity.class);
    }

    @Test
    public void testToDbo_nullDomain() {
        // Test para cuando el domain (Transaction) es null
        TransactionEntity entity = transactionDbMapper.toDbo(null);

        // Verifica que el resultado sea null
        assertNull(entity, "El resultado debe ser null cuando el domain es null");
    }

    @Test
    public void testToDbo_nonNullDomain() {
        // Configura el mock de Transaction con valores no nulos
        UUID transactionId = UUID.randomUUID();
        when(mockTransaction.getId()).thenReturn(transactionId);
        when(mockTransaction.getCustomer()).thenReturn(mockCustomer);
        when(mockTransaction.getFund()).thenReturn(mockFund);
        when(mockTransaction.getTransactionType()).thenReturn(TransactionType.APERTURA);
        when(mockTransaction.getTransactionDate()).thenReturn(LocalDate.now());
        when(mockTransaction.getAmount()).thenReturn(BigDecimal.valueOf(1000));

        // Llama al método toDbo con un objeto Transaction no nulo
        TransactionEntity entity = transactionDbMapper.toDbo(mockTransaction);

        // Verifica que el objeto TransactionEntity no sea null
        assertNotNull(entity, "El resultado no debe ser null cuando el domain no es null");

        // Verifica que los valores se mapeen correctamente
        assertEquals(transactionId, entity.getId()); // Verifica que el UUID sea el mismo
        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getFund());
        assertEquals(TransactionType.APERTURA, entity.getTransactionType());
        assertEquals(LocalDate.now(), entity.getTransactionDate());
        assertEquals(BigDecimal.valueOf(1000), entity.getAmount());
    }

    @Test
    public void testToDomain_nullEntity() {
        // Test para cuando el entity (TransactionEntity) es null
        Transaction transaction = transactionDbMapper.toDomain(null);

        // Verifica que el resultado sea null
        assertNull(transaction, "El resultado debe ser null cuando el entity es null");
    }


    // Asegúrate de que las funciones auxiliares como customerToCustomerEntity y fundToFundEntity funcionen
    @Test
    public void testCustomerToCustomerEntity_null() {
        assertNull(transactionDbMapper.customerToCustomerEntity(null), "Debería retornar null si el customer es null");
    }

    @Test
    public void testFundToFundEntity_null() {
        assertNull(transactionDbMapper.fundToFundEntity(null), "Debería retornar null si el fund es null");
    }

    @Test
    public void testCustomerEntityToCustomer_null() {
        assertNull(transactionDbMapper.customerEntityToCustomer(null), "Debería retornar null si el customerEntity es null");
    }

    @Test
    public void testFundEntityToFund_null() {
        assertNull(transactionDbMapper.fundEntityToFund(null), "Debería retornar null si el fundEntity es null");
    }

    @Test
    public void testToDomain_nonNullEntity() {
        UUID transactionId = UUID.randomUUID();
        when(mockTransactionEntity.getId()).thenReturn(transactionId);
        when(mockTransactionEntity.getCustomer()).thenReturn(mockCustomerEntity);
        when(mockTransactionEntity.getFund()).thenReturn(mockFundEntity);
        when(mockTransactionEntity.getTransactionType()).thenReturn(TransactionType.APERTURA);
        when(mockTransactionEntity.getTransactionDate()).thenReturn(LocalDate.now());
        when(mockTransactionEntity.getAmount()).thenReturn(BigDecimal.valueOf(1000));

        // Llama al método toDomain con un objeto TransactionEntity no nulo
        Transaction transaction = transactionDbMapper.toDomain(mockTransactionEntity);

        // Verifica que el objeto Transaction no sea null
        assertNotNull(transaction, "El resultado no debe ser null cuando el entity no es null");

        // Verifica que los valores se mapeen correctamente
        assertEquals(transactionId, transaction.getId()); // Verifica que el UUID sea el mismo
        assertNotNull(transaction.getCustomer());
        assertNotNull(transaction.getFund());
        assertEquals(TransactionType.APERTURA, transaction.getTransactionType());
        assertEquals(LocalDate.now(), transaction.getTransactionDate());
        assertEquals(BigDecimal.valueOf(1000), transaction.getAmount());
    }
}

