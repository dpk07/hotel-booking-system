package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomSecurityService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final PrincipalHelper principalHelper;
    public RoomSecurityService(RoomTypeRepository roomTypeRepository,
                               RoomRepository roomRepository,
                               PrincipalHelper principalHelper) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
        this.principalHelper = principalHelper;
    }
    /**
     * Checks if user is authorized to edit this room.
     *
     * <p>This method checks if the room provided by the user is associated with the same hotel that he/she is a part of.</p>
     * @param room Room Type that the user wishes to edit.
     * @return true if the user is authorized to edit the provided roomType.
     */
    public boolean hasAccessToEditRoom(Room room) {
        Long hotelId = principalHelper.getHotelId();
        if(room.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Room ID required to edit room.");
        }
        int count = roomRepository.countByRoomIdAndHotelId(hotelId,room.getId());
        return count==1;
    }

    /**
     * Checks if user is authorized to create this room.
     *
     * <p>This method checks if Room Type Id associated to the users Hotel Id.</p>
     * @param roomTypeId Room Type Id where the user wants to create room.
     * @return true if the user is authorized to create a room in the provided room type.
     */
    public boolean hasAccessToCreateRoom(Long roomTypeId) {
        Long hotelId = principalHelper.getHotelId();
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomTypeId);
        return count==1;
    }

}
