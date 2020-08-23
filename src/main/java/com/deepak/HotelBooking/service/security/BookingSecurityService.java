package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.Hotel;
import com.deepak.HotelBooking.repository.BookingRepository;
import com.deepak.HotelBooking.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BookingSecurityService {
    BookingRepository bookingRepository;
    RoomRepository roomRepository;
    PrincipalHelper principalHelper;
    public BookingSecurityService(BookingRepository bookingRepository, RoomRepository roomRepository, PrincipalHelper principalHelper) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.principalHelper = principalHelper;
    }
    /**
     * Checks if user is authorized to edit this booking.
     *
     * <p>This method checks if Booking Room Id is associated to the users Hotel Id.</p>
     * @param booking Booking which the user wants to edit.
     * @return true if the user is authorized to edit a booking in the provided room.
     */
    public boolean hasAccessToEditBooking(Booking booking) {
        Long hotelId = principalHelper.getHotelId();
        Hotel hotel = booking.getHotel();
        if(hotel!=null && hotel.getId()!=null){
            if(!hotel.getId().equals(hotelId)){
                return false;
            }
        }
        if(booking.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Booking ID required to edit booking.");
        }
        int count = bookingRepository.countByBookingIdAndHotelId(hotelId,booking.getId());
        return count==1;
    }
    /**
     * Checks if user is authorized to create this booking.
     *
     * <p>This method checks if Booking Room Id is associated to the users Hotel Id.</p>
     * @param booking Booking which the user wants to create.
     * @return true if the user is authorized to create a booking in the provided room.
     */
    public boolean hasAccessToCreateBooking(Booking booking) {
        Long hotelId = principalHelper.getHotelId();
        Long roomId = booking.getRoom().getId();
        int count = roomRepository.countByRoomIdAndHotelId(hotelId,roomId);
        return count==1;
    }



}
