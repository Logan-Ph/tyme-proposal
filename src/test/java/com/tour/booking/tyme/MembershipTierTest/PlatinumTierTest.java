package com.tour.booking.tyme.MembershipTierTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tour.booking.tyme.service.MembershipTier.PlatinumTier;

public class PlatinumTierTest {

    @Test
    public void testGetTierName() {
        PlatinumTier tier = new PlatinumTier();
        assertEquals("Platinum", tier.getTierName());
    }

    @Test
    public void testGetDiscountRate() {
        PlatinumTier tier = new PlatinumTier();
        assertEquals(0.15f, tier.getDiscountRate());
    }
}