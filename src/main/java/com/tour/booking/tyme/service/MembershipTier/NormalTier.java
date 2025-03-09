package com.tour.booking.tyme.service.MembershipTier;

public class NormalTier implements MembershipTier {
    @Override
    public String getTierName() {
        return "Normal";
    }

    @Override
    public float getDiscountRate() {
        return 0.0f; // No discount for normal tier
    }
}
