package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.Hotel;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.policy.RoomTypePolicy;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypePolicy roomTypePolicy;
    private final PrincipalHelper principalHelper;
    @Value("${roomType.ErrorMessage}")
    private String roomTypeErrorMessage = "";
    public RoomTypeService(RoomTypeRepository roomTypeRepository, RoomTypePolicy roomTypePolicy, PrincipalHelper principalHelper) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomTypePolicy = roomTypePolicy;
        this.principalHelper = principalHelper;
    }

    public List<RoomType> getAllRoomTypes(){
        Long hotelId = principalHelper.getHotelId();
        return roomTypeRepository.findAllByHotelId(hotelId);
    }
    @PreAuthorize("@roomTypeSecurityService.hasAccessToCreateRoomType(#hotelId)")
    public RoomType addRoomType(RoomType roomType,Long hotelId){
        if(roomTypePolicy.canAddRoom(hotelId)){
            Hotel hotel = new Hotel();
            hotel.setId(hotelId);
            roomType.setHotel(hotel);
            return roomTypeRepository.save(roomType);
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, roomTypeErrorMessage);
    }
    @PreAuthorize("@roomTypeSecurityService.hasAccessToEditRoomType(#roomType)")
    public RoomType editRoomType(RoomType roomType){
        if(roomType.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Room Type Id");

        return roomTypeRepository.save(roomType);
    }
    @PreAuthorize("@roomTypeSecurityService.hasAccessToEditRoomType(#roomType)")
    public void deleteRoomType(RoomType roomType){
        roomTypeRepository.delete(roomType);
    }
}
