package com.tour.booking.tyme.MembershipTierTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tour.booking.tyme.service.MembershipTier.NormalTier;

public class NormalTierTest {

    @Test
    public void testGetTierName() {
        NormalTier tier = new NormalTier();
        assertEquals("Normal", tier.getTierName());
    }

    @Test
    public void testGetDiscountRate() {
        NormalTier tier = new NormalTier();
        assertEquals(0.0f, tier.getDiscountRate());
    }
}