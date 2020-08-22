package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.configuration.HotelIdHelper;
import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.Hotel;
import com.deepak.HotelBooking.repository.BookingRepository;
import com.deepak.HotelBooking.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingSecurityService {
    BookingRepository bookingRepository;
    RoomRepository roomRepository;
    HotelIdHelper hotelIdHelper;
    public BookingSecurityService(BookingRepository bookingRepository, RoomRepository roomRepository,HotelIdHelper hotelIdHelper) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.hotelIdHelper = hotelIdHelper;
    }

    public boolean hasAccessToEditBooking(Booking booking) {
        Long hotelId = hotelIdHelper.getHotelId();
        Hotel hotel = booking.getHotel();
        if(hotel!=null && hotel.getId()!=null){
            if(!hotel.getId().equals(hotelId)){
                return false;
            }
        }
        int count = bookingRepository.countByBookingIdAndHotelId(hotelId,booking.getId());
        return count==1;
    }

    public boolean hasAccessToCreateBooking(Booking booking) {
        Long hotelId = hotelIdHelper.getHotelId();
        Long roomId = booking.getRoom().getId();
        int count = roomRepository.countByRoomIdAndHotelId(hotelId,roomId);
        return count==1;
    }


}
