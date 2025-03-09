package com.tour.booking.tyme.service.MembershipTier;

public class PlatinumTier implements MembershipTier {
    @Override
    public String getTierName() {
        return "Platinum";
    }

    @Override
    public float getDiscountRate() {
        return 0.15f; // 15% discount for platinum customers
    }
}
