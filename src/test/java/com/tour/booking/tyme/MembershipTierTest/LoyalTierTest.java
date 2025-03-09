package com.tour.booking.tyme.MembershipTierTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tour.booking.tyme.service.MembershipTier.LoyalTier;

public class LoyalTierTest {

    @Test
    public void testGetTierName() {
        LoyalTier tier = new LoyalTier();
        assertEquals("Loyal", tier.getTierName());
    }

    @Test
    public void testGetDiscountRate() {
        LoyalTier tier = new LoyalTier();
        assertEquals(0.05f, tier.getDiscountRate());
    }
}