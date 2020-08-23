package com.deepak.HotelBooking.policy;

import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FifteenRoomTypePolicy implements RoomTypePolicy {
    private RoomTypeRepository roomTypeRepository;

    @Value("${roomType.ROOM_TYPE_THRESHOLD}")
    private int ROOM_TYPE_THRESHOLD = 15;

    public FifteenRoomTypePolicy(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    /**
     * Checks if more room types can be added.
     * <p>Checks if the current room types are less than provided value of 15</p>
     * @param hotelId HotelId of the hotel where the room type needs to be added.
     * @return true if the total room types are less than 15.
     */
    @Override
    public boolean canAddRoom(Long hotelId) {
        int count = roomTypeRepository.countAllByHotelId(hotelId);
        return count<ROOM_TYPE_THRESHOLD;
    }
}
