package com.tour.booking.tyme.DiscountStrategyTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tour.booking.tyme.service.Discount.DiscountStrategy;
import com.tour.booking.tyme.service.Discount.DiscountStrategyFactory;
import com.tour.booking.tyme.service.Discount.DiscountType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountStrategyTest {

    private DiscountStrategyFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new DiscountStrategyFactory();
    }

    @Test
    public void testFixedDiscountStrategy() {
        BigDecimal originalPrice = new BigDecimal("100.00");
        BigDecimal discountAmount = new BigDecimal("10.00");

        DiscountStrategy fixedDiscount = factory.createStrategy(DiscountType.FIXED, discountAmount);
        BigDecimal discountedPrice = fixedDiscount.calculateDiscount(originalPrice);

        assertEquals(new BigDecimal("90.00"), discountedPrice);
    }

    @Test
    public void testPercentageDiscountStrategy() {
        BigDecimal originalPrice = new BigDecimal("100.00");
        BigDecimal discountPercentage = new BigDecimal("20");

        DiscountStrategy percentageDiscount = factory.createStrategy(DiscountType.PERCENTAGE, discountPercentage);
        BigDecimal discountedPrice = percentageDiscount.calculateDiscount(originalPrice);

        assertEquals(new BigDecimal("80.00"), discountedPrice);
    }

    @Test
    public void testInvalidDiscountType() {
        assertThrows(NullPointerException.class, () -> {
            factory.createStrategy(null, new BigDecimal("10.00"));
        });
    }
}