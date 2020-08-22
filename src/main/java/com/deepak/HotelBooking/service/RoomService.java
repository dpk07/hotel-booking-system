package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
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

    public Room addRoom(Room room,Long roomTypeId) {
        checkPermissionForAdd(roomTypeId,1L);
        return roomRepository.save(room);
    }

    public Room editRoom(Room room) {
        if(room.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Room Id");
        checkPermissionForUpdate(room,1L);
        return roomRepository.save(room);
    }

    public void deleteRoom(Room room) {
        checkPermissionForUpdate(room,1L);
        roomRepository.delete(room);
    }
    private void checkPermissionForUpdate(Room room, Long hotelId){
        int count = roomRepository.countByRoomTypeIdAndHotelId(hotelId,room.getId());
        if(count==0)throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "You do not have authority to access this room");
    }
    private void checkPermissionForAdd(Long roomTypeId,Long hotelId){
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomTypeId);
        if(count==0)throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "You do not have authority to access this room type");
    }

}
