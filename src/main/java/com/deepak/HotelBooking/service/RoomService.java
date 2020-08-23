package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.*;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final PrincipalHelper principalHelper;
    public RoomService(RoomRepository roomRepository,
                       RoomTypeRepository roomTypeRepository,
                       PrincipalHelper principalHelper) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.principalHelper = principalHelper;
    }
    @PreAuthorize("@roomSecurityService.hasAccessToCreateRoom(#roomTypeId)")
    public Room addRoom(Room room,Long roomTypeId) {
        Long hotelId = principalHelper.getHotelId();
        room.setRoomType(new RoomType(roomTypeId));
        room.setHotel(new Hotel(hotelId));
        Room savedRoom = roomRepository.save(room);
        if(savedRoom==null)throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Room could not be added");
        return roomRepository.findById(savedRoom.getId()).get();
    }
    @PreAuthorize("@roomSecurityService.hasAccessToEditRoom(#room)")
    public Room editRoom(Room room) {
        if(room.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Room Id");
        Optional<Room> prevObject = roomRepository.findById(room.getId());
        if(prevObject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Room you are trying to edit is not found");
        }
        Room prevRoom = prevObject.get();
        if(room.getHotel()!=null && room.getHotel().getId()!=null){
            prevRoom.setHotel(room.getHotel());
        }
        if(room.getRoomType()!=null && room.getRoomType().getId()!=null){
            prevRoom.setRoomType(room.getRoomType());
        }
        if(room.getName()!=null){
            prevRoom.setName(room.getName());
        }
        return roomRepository.save(prevRoom);
    }
    @PreAuthorize("@roomSecurityService.hasAccessToEditRoom(#room)")
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    @PreAuthorize("@roomSecurityService.hasAccessToCreateRoom(#roomTypeId)")
    public List<Room> findRooms(DateRange dateRange, Long roomTypeId){
        return roomRepository.findByRoomTypeIdAndDateRange(roomTypeId,
                dateRange.getStartDate(),
                dateRange.getEndDate());
    }
}
