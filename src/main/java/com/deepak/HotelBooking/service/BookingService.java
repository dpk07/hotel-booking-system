package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.DateRange;
import com.deepak.HotelBooking.policy.PricePolicy;
import com.deepak.HotelBooking.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PricePolicy pricePolicy;
    public BookingService(BookingRepository bookingRepository,PricePolicy pricePolicy) {
        this.bookingRepository = bookingRepository;
        this.pricePolicy = pricePolicy;
    }

    public List<Booking> getBookingsByDateRange(DateRange dateRange){
        return bookingRepository.findByDateRange(dateRange.getStartDate(),dateRange.getEndDate());
    }
    public BigDecimal getPrice(Booking booking){
    return pricePolicy.getPrice(booking);
    }

    public Booking addBooking (Booking booking){
        BigDecimal totalPrice = pricePolicy.getPrice(booking);
        booking.setPrice(totalPrice);
        return bookingRepository.save(booking);
    }
    public Booking editBooking(Booking booking){
        if(booking.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Booking Id");
        BigDecimal totalPrice = pricePolicy.getPrice(booking);
        booking.setPrice(totalPrice);
        return bookingRepository.save(booking);
    }
    public void deleteBooking(Booking booking){
        if(booking.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Booking Id");
        bookingRepository.delete(booking);
    }

}
