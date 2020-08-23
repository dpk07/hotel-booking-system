package com.deepak.HotelBooking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class PriceSurge extends DateRange{
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JsonIgnore
    private Hotel hotel;
    @ManyToOne(optional = false)
    private RoomType roomType;
    @NotNull
    private BigDecimal price;

    public PriceSurge() {
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public PriceSurge(RoomType roomType, LocalDate startDate, LocalDate endDate, BigDecimal price){
        super(startDate,endDate);
        this.roomType = roomType;
        this.price = price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
