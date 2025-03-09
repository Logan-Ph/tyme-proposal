package com.tour.booking.tyme.service.MembershipTier;

import java.math.BigDecimal;

public class MembershipTierFactory {
    public MembershipTier createTier(BigDecimal totalSpending) {
        MembershipTierType tierType = MembershipTierType.fromSpending(totalSpending);
        
        switch (tierType) {
            case NORMAL:
                return new NormalTier();
            case LOYAL:
                return new LoyalTier();
            case VIP:
                return new VIPTier();
            case PLATINUM:
                return new PlatinumTier();
            default:
                throw new IllegalArgumentException("Invalid tier type: " + tierType);
        }
    }
}
