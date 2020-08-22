package com.deepak.HotelBooking.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class PriceSurge extends BaseEntity{
    @ManyToOne
    private RoomType roomType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;

    public PriceSurge() {
    }
    public PriceSurge(RoomType roomType,LocalDate startDate,LocalDate endDate,BigDecimal price){
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
