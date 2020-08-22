package com.deepak.HotelBooking.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Receptionist extends BaseEntity {
    @ManyToOne
    private Hotel hotel;
    private String name;

    public Receptionist() {
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
