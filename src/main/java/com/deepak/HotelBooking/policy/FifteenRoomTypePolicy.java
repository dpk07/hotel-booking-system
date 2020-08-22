package com.deepak.HotelBooking.policy;

import com.deepak.HotelBooking.repository.RoomTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class FifteenRoomTypePolicy implements RoomTypePolicy {
    private RoomTypeRepository roomTypeRepository;

    public FifteenRoomTypePolicy(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public boolean canAddRoom(Long hotelId) {
        int count = roomTypeRepository.countAllByHotelId(hotelId);
        return count<15;
    }
}
