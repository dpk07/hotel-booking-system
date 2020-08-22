package com.deepak.HotelBooking.policy;

import com.deepak.HotelBooking.model.Booking;

import java.math.BigDecimal;

public interface PricePolicy {
    BigDecimal getPrice(Booking booking);
}
