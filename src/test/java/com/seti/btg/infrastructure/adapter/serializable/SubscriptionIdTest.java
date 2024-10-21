package com.seti.btg.infrastructure.adapter.serializable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionIdTest {
    @Test
    public void testEquals_SameObject() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);

        // Act & Assert
        assertTrue(id1.equals(id1), "The object should be equal to itself.");
    }

    @Test
    public void testEquals_DifferentObjects_SameValues() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);
        SubscriptionId id2 = new SubscriptionId(1L, 101L);

        // Act & Assert
        assertTrue(id1.equals(id2), "Objects with the same values should be equal.");
    }

    @Test
    public void testEquals_DifferentObjects_DifferentValues() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);
        SubscriptionId id2 = new SubscriptionId(2L, 102L);

        // Act & Assert
        assertFalse(id1.equals(id2), "Objects with different values should not be equal.");
    }

    @Test
    public void testEquals_Null() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);

        // Act & Assert
        assertFalse(id1.equals(null), "The object should not be equal to null.");
    }

    @Test
    public void testEquals_DifferentClass() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);
        String notASubscriptionId = "Not a SubscriptionId";

        // Act & Assert
        assertFalse(id1.equals(notASubscriptionId), "The object should not be equal to an instance of a different class.");
    }

    @Test
    public void testHashCode_SameValues() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);
        SubscriptionId id2 = new SubscriptionId(1L, 101L);

        // Act & Assert
        assertEquals(id1.hashCode(), id2.hashCode(), "Objects with the same values should have the same hash code.");
    }

    @Test
    public void testHashCode_DifferentValues() {
        // Arrange
        SubscriptionId id1 = new SubscriptionId(1L, 101L);
        SubscriptionId id2 = new SubscriptionId(2L, 102L);

        // Act & Assert
        assertNotEquals(id1.hashCode(), id2.hashCode(), "Objects with different values should have different hash codes.");
    }

    @Test
    public void testEquals_SameIdFund() {
        // Arrange
        Long idCustomer = 1L;
        Long idFund = 101L;

        SubscriptionId id1 = new SubscriptionId(idCustomer, idFund);
        SubscriptionId id2 = new SubscriptionId(idCustomer, idFund);

        // Act & Assert
        assertTrue(id1.equals(id2), "Objects with the same idFund value should be equal.");
    }

    @Test
    public void testEquals_DifferentIdFund() {
        // Arrange
        Long idCustomer = 1L;
        Long idFund1 = 101L;
        Long idFund2 = 102L;

        SubscriptionId id1 = new SubscriptionId(idCustomer, idFund1);
        SubscriptionId id2 = new SubscriptionId(idCustomer, idFund2);

        // Act & Assert
        assertFalse(id1.equals(id2), "Objects with different idFund values should not be equal.");
    }
}
