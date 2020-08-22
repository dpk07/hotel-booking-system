package com.deepak.HotelBooking.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Room extends BaseEntity{
    @ManyToOne
    private RoomType roomType;
    @ManyToOne
    private Hotel hotel;
    private String name;

    public Room() {
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
