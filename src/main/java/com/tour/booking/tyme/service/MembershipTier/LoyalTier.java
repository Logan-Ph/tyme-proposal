package com.tour.booking.tyme.service.MembershipTier;

public class LoyalTier implements MembershipTier {
    @Override
    public String getTierName() {
        return "Loyal";
    }

    @Override
    public float getDiscountRate() {
        return 0.05f; // 5% discount for loyal customers
    }
}
