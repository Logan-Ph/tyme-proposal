package com.tour.booking.tyme.service.Discount;

import java.math.BigDecimal;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private BigDecimal discountPercentage;

    public PercentageDiscountStrategy(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal originalPrice) {
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage).divide(new BigDecimal("100"));
        return originalPrice.subtract(discountAmount);
    }
}
