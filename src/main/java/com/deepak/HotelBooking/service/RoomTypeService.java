package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.policy.RoomTypePolicy;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoomTypeService {
    private RoomTypeRepository roomTypeRepository;
    private RoomTypePolicy roomTypePolicy;
    @Value("${roomType.ErrorMessage}")
    private String roomTypeErrorMessage = "";
    public RoomTypeService(RoomTypeRepository roomTypeRepository, RoomTypePolicy roomTypePolicy) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomTypePolicy = roomTypePolicy;
    }

    public List<RoomType> getAllRoomTypesByHotelId(Long hotelId){
        return roomTypeRepository.findAllByHotelId(hotelId);
    }

    public RoomType addRoomType(RoomType roomType,Long hotelId){
        if(roomTypePolicy.canAddRoom(hotelId)){
            return roomTypeRepository.save(roomType);
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, roomTypeErrorMessage);
    }
    public RoomType editRoomType(RoomType roomType){
        if(roomType.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Room Type Id");
        return roomTypeRepository.save(roomType);
    }
    public void deleteRoomType(RoomType roomType){
        roomTypeRepository.delete(roomType);
    }
}
