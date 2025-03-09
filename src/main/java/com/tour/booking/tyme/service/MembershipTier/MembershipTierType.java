package com.tour.booking.tyme.service.MembershipTier;

import java.math.BigDecimal;

public enum MembershipTierType {
    NORMAL("Normal", BigDecimal.ZERO, 0.0f),
    LOYAL("Loyal", new BigDecimal("1000.00"), 0.05f),
    VIP("VIP", new BigDecimal("5000.00"), 0.1f),
    PLATINUM("Platinum", new BigDecimal("10000.00"), 0.15f);
    
    private final String tierName;
    private final BigDecimal threshold;
    private final float discountRate;
    
    MembershipTierType(String tierName, BigDecimal threshold, float discountRate) {
        this.tierName = tierName;
        this.threshold = threshold;
        this.discountRate = discountRate;
    }
    
    public String getTierName() {
        return tierName;
    }
    
    public BigDecimal getThreshold() {
        return threshold;
    }
    
    public float getDiscountRate() {
        return discountRate;
    }
    
    /**
     * Find the highest tier that applies to the given spending amount
     */
    public static MembershipTierType fromSpending(BigDecimal totalSpending) {
        if (totalSpending == null) {
            return NORMAL;
        }
        
        // Check tiers in reverse order (highest to lowest)
        for (int i = values().length - 1; i >= 0; i--) {
            MembershipTierType tierType = values()[i];
            if (totalSpending.compareTo(tierType.getThreshold()) >= 0) {
                return tierType;
            }
        }
        
        return NORMAL; // Default tier
    }
}
