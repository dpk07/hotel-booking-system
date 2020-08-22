package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    public RoomService(RoomRepository roomRepository,RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }
    @PreAuthorize("@roomSecurityService.hasAccessToCreateRoom(#roomTypeId)")
    public Room addRoom(Room room,Long roomTypeId) {
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        room.setRoomType(roomType);
        return roomRepository.save(room);
    }
    @PreAuthorize("@roomSecurityService.hasAccessToEditRoom(#room)")
    public Room editRoom(Room room) {
        if(room.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Room Id");
        return roomRepository.save(room);
    }
    @PreAuthorize("@roomSecurityService.hasAccessToEditRoom(#room)")
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

}
