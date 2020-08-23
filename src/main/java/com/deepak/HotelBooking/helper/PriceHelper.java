package com.deepak.HotelBooking.helper;

import com.deepak.HotelBooking.model.PriceSurge;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PriceHelper {
    BigDecimal calculatePrice(List<PriceSurge> priceSurgeList,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     BigDecimal pricePerNight) throws IllegalArgumentException;
}
