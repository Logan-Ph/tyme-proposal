package com.tour.booking.tyme.service.MembershipTier;

public class VIPTier implements MembershipTier {
    @Override
    public String getTierName() {
        return "VIP";
    }

    @Override
    public float getDiscountRate() {
        return 0.1f; // 10% discount for VIP customers
    }
}
