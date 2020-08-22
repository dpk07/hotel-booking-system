package com.deepak.HotelBooking.policy;

public interface RoomTypePolicy {
    boolean canAddRoom(Long hotelId);
}
