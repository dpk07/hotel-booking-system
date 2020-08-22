package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.configuration.HotelIdHelper;
import com.deepak.HotelBooking.model.Hotel;
import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HotelIdHelper hotelIdHelper;
    public RoomService(RoomRepository roomRepository,
                       RoomTypeRepository roomTypeRepository,
                       HotelIdHelper hotelIdHelper) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.hotelIdHelper = hotelIdHelper;
    }
    @PreAuthorize("@roomSecurityService.hasAccessToCreateRoom(#roomTypeId)")
    public Room addRoom(Room room,Long roomTypeId) {
        Long hotelId = hotelIdHelper.getHotelId();
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        room.setRoomType(roomType);
        room.setHotel(hotel);
        return roomRepository.save(room);
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

}
