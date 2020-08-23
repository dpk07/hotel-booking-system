package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomSecurityService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    public RoomSecurityService(RoomTypeRepository roomTypeRepository, RoomRepository roomRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
    }

    public boolean hasAccessToEditRoom(Room room) {
        Long hotelId = getHotelId();
        if(room.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Room ID required to edit room.");
        }
        int count = roomRepository.countByRoomIdAndHotelId(hotelId,room.getId());
        return count==1;
    }

    public boolean hasAccessToCreateRoom(Long roomTypeId) {
        Long hotelId = getHotelId();
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomTypeId);
        return count==1;
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
