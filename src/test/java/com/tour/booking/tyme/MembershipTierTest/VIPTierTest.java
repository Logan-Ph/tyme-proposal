package com.tour.booking.tyme.MembershipTierTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tour.booking.tyme.service.MembershipTier.VIPTier;

public class VIPTierTest {

    @Test
    public void testGetTierName() {
        VIPTier tier = new VIPTier();
        assertEquals("VIP", tier.getTierName());
    }

    @Test
    public void testGetDiscountRate() {
        VIPTier tier = new VIPTier();
        assertEquals(0.1f, tier.getDiscountRate());
    }
}