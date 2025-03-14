package com.tour.booking.tyme.service.Discount;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;


@Component
public class DiscountStrategyFactory {
    public DiscountStrategy createStrategy(DiscountType type, BigDecimal value) {
        switch (type) {
            case FIXED:
                return new FixedDiscountStrategy(value);
            case PERCENTAGE:
                return new PercentageDiscountStrategy(value);
            default:
                throw new NullPointerException("Invalid discount type: " + type);
        }
    }
}
