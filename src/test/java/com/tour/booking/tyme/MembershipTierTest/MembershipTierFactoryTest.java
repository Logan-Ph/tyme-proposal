package com.tour.booking.tyme.MembershipTierTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

import com.tour.booking.tyme.service.MembershipTier.*;

public class MembershipTierFactoryTest {

    private MembershipTierFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new MembershipTierFactory();
    }

    @Test
    public void testCreateTierWithNull() {
        MembershipTier tier = factory.createTier(null);
        assertNotNull(tier);
        assertTrue(tier instanceof NormalTier);
    }

    @Test
    public void testCreateTierWithLowSpending() {
        MembershipTier tier = factory.createTier(new BigDecimal("500.00"));
        assertNotNull(tier);
        assertTrue(tier instanceof NormalTier);
    }

    @Test
    public void testCreateTierWithLoyalSpending() {
        MembershipTier tier = factory.createTier(new BigDecimal("1500.00"));
        assertNotNull(tier);
        assertTrue(tier instanceof LoyalTier);
    }

    @Test
    public void testCreateTierWithVIPSpending() {
        MembershipTier tier = factory.createTier(new BigDecimal("7500.00"));
        assertNotNull(tier);
        assertTrue(tier instanceof VIPTier);
    }

    @Test
    public void testCreateTierWithPlatinumSpending() {
        MembershipTier tier = factory.createTier(new BigDecimal("12000.00"));
        assertNotNull(tier);
        assertTrue(tier instanceof PlatinumTier);
    }
}