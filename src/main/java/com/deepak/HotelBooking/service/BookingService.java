package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.configuration.HotelIdHelper;
import com.deepak.HotelBooking.model.*;
import com.deepak.HotelBooking.policy.PricePolicy;
import com.deepak.HotelBooking.repository.BookingRepository;
import com.deepak.HotelBooking.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PricePolicy pricePolicy;
    private final HotelIdHelper hotelIdHelper;
    private final RoomRepository roomRepository;
    public BookingService(BookingRepository bookingRepository,
                          PricePolicy pricePolicy,
                          HotelIdHelper hotelIdHelper,
                          RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.pricePolicy = pricePolicy;
        this.hotelIdHelper = hotelIdHelper;
        this.roomRepository = roomRepository;
    }

    public List<Booking> getBookingsByDateRange(DateRange dateRange){
        Long hotelId = hotelIdHelper.getHotelId();
        return bookingRepository.findByHotelIdAndDateRange(dateRange.getStartDate(),dateRange.getEndDate(),hotelId);
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
        checkAvailability(booking);
        Long hotelId = hotelIdHelper.getHotelId();
        BigDecimal totalPrice = pricePolicy.getPrice(booking);
        booking.setPrice(totalPrice);
        booking.setHotel(new Hotel(hotelId));
        booking.setReceptionist(new Receptionist(hotelIdHelper.getUserId()));
        booking.setNumberOfNights(calculateNumberOfNights(booking));
        Booking completed = bookingRepository.save(booking);
        if(completed==null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Booking could not be completed.");
        }
        return bookingRepository.findById(completed.getId()).get();
    }

    private Long calculateNumberOfNights(Booking booking) {
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    private boolean checkAvailability(Booking booking){
        Optional<Room> room = roomRepository.findByRoomIdRoomIdAndDateRange(booking.getRoom().getId(),
                booking.getStartDate(),
                booking.getEndDate());
        if(room.isPresent()){
            return true;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "This room is no longer available");
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
