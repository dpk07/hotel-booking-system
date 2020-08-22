package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityService {
    private final RoomTypeRepository roomTypeRepository;

    public SecurityService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public boolean hasAccessToRoomType(RoomType roomType,Long hotelId) {
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomType.getId());
        return count==1;
    }
}
