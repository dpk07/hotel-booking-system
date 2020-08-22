package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.model.Hotel;
import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomTypeSecurityService {
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeSecurityService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public boolean hasAccessToEditRoomType(RoomType roomType) {
        Long hotelId = getHotelId();
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomType.getId());
        return count==1;
    }

    public boolean hasAccessToCreateRoomType(Long hotelId) {
        Long principalHotelId = getHotelId();
        return principalHotelId==hotelId;
    }

    private Long getHotelId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long hotelId=null;
        if (principal instanceof Receptionist) {
            hotelId = ((Receptionist)principal).getHotel().getId();
        }
        return hotelId;
    }
}
