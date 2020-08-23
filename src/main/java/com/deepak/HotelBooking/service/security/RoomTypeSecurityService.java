package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomTypeSecurityService {
    private final RoomTypeRepository roomTypeRepository;
    private final PrincipalHelper principalHelper;
    public RoomTypeSecurityService(RoomTypeRepository roomTypeRepository, PrincipalHelper principalHelper) {
        this.roomTypeRepository = roomTypeRepository;
        this.principalHelper = principalHelper;
    }
    /**
     * Checks if user is authorized to edit this room type.
     *
     * <p>This method checks if the roomType provided by the user is associated with the same hotel that he/she is a part of.</p>
     * @param roomType Room Type that the user wishes to edit.
     * @return true if the user is authorized to edit the provided roomType.
     */
    public boolean hasAccessToEditRoomType(RoomType roomType) {
        Long hotelId = principalHelper.getHotelId();
        if(roomType.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Room Type ID required to edit room type.");
        }
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomType.getId());
        return count==1;
    }

    /**
     * Checks if user is authorized to create this room type.
     *
     * <p>This method checks if HotelId is associated to the users Hotel Id.</p>
     * @param hotelId Hotel Id where the user wants to create room type
     * @return true if the user is authorized to create a room type in the provided hotel.
     */
    public boolean hasAccessToCreateRoomType(Long hotelId) {
        Long principalHotelId = principalHelper.getHotelId();
        return principalHotelId.equals(hotelId) ;
    }

}
