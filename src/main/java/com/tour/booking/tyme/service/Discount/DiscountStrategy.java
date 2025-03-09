package com.tour.booking.tyme.service.Discount;

import java.math.BigDecimal;

public interface DiscountStrategy {
    public BigDecimal calculateDiscount(BigDecimal originalPrice);
} 
