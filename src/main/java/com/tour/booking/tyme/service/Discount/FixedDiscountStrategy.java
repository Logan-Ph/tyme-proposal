package com.tour.booking.tyme.service.Discount;

import java.math.BigDecimal;

public class FixedDiscountStrategy implements DiscountStrategy {
    private BigDecimal discountAmount;

    public FixedDiscountStrategy(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal originalPrice) {
        return originalPrice.subtract(discountAmount);
    }
}
