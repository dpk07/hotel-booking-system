package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.configuration.HotelIdHelper;
import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.DateRange;
import com.deepak.HotelBooking.model.Hotel;
import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.policy.PricePolicy;
import com.deepak.HotelBooking.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PricePolicy pricePolicy;
    private final HotelIdHelper hotelIdHelper;
    public BookingService(BookingRepository bookingRepository,PricePolicy pricePolicy,HotelIdHelper hotelIdHelper) {
        this.bookingRepository = bookingRepository;
        this.pricePolicy = pricePolicy;
        this.hotelIdHelper = hotelIdHelper;
    }

    public List<Booking> getBookingsByDateRange(DateRange dateRange){
        return bookingRepository.findByDateRange(dateRange.getStartDate(),dateRange.getEndDate());
    }
    @PreAuthorize("@bookingSecurityService.hasAccessToCreateBooking(#booking)")
    public BigDecimal getPrice(Booking booking){
    return pricePolicy.getPrice(booking);
    }
    @PreAuthorize("@bookingSecurityService.hasAccessToCreateBooking(#booking)")
    public Booking addBooking (Booking booking){
        if(booking.getRoom().getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please provide Room Id");
        }
        Long hotelId = hotelIdHelper.getHotelId();
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        BigDecimal totalPrice = pricePolicy.getPrice(booking);
        booking.setPrice(totalPrice);
        booking.setHotel(hotel);
        return bookingRepository.save(booking);
    }
    @PreAuthorize("@bookingSecurityService.hasAccessToEditBooking(#booking)")
    public Booking editBooking(Booking booking){
        if(booking.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Booking Id");
        BigDecimal totalPrice = pricePolicy.getPrice(booking);
        Optional<Booking> prevObject = bookingRepository.findById(booking.getId());
        if(prevObject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Booking you are trying to edit is not found");
        }
        Booking prevBooking = prevObject.get();
        if(booking.getRoom()!=null && booking.getRoom().getId()!=null){
            prevBooking.setRoom(booking.getRoom());
        }
        if(booking.getStartDate()!=null){
            prevBooking.setStartDate(booking.getStartDate());
        }
        if(booking.getEndDate()!=null){
            prevBooking.setEndDate(booking.getEndDate());
        }
        if(booking.getCustomer()!=null){
            prevBooking.setCustomer(booking.getCustomer());
        }
        if(booking.getHotel()!=null && booking.getHotel().getId()!=null){
            prevBooking.setHotel(booking.getHotel());
        }
        prevBooking.setPrice(totalPrice);
        return bookingRepository.save(prevBooking);
    }
    @PreAuthorize("@bookingSecurityService.hasAccessToEditBooking(#booking)")
    public void deleteBooking(Booking booking){
        if(booking.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Booking Id");
        bookingRepository.delete(booking);
    }

}
