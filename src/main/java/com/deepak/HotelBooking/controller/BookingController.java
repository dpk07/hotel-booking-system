package com.deepak.HotelBooking.controller;

import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.DateRange;
import com.deepak.HotelBooking.service.BookingService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public Booking addBooking(@Valid @RequestBody Booking booking){
        return bookingService.addBooking(booking);
    }
    @PutMapping
    public Booking editBooking(@RequestBody Booking booking){
        return bookingService.editBooking(booking);
    }
    @DeleteMapping
    public void deleteBooking(@RequestBody Booking booking){
        bookingService.deleteBooking(booking);
    }

    @PostMapping("/date")
    public List<Booking> getBookingsByDateRange(@RequestBody DateRange dateRange){
        return bookingService.getBookingsByDateRange(dateRange);
    }
    @PostMapping("/price")
    public BigDecimal getPrice(@RequestBody Booking booking){
        return bookingService.getPrice(booking);
    }
}
