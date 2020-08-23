package com.deepak.HotelBooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Booking extends DateRange {
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JsonIgnore
    private Hotel hotel;
    @ManyToOne(optional = false)
    private Room room;
    Long numberOfNights;
    private BigDecimal price;

    @ManyToOne(optional = false)
    private Customer customer;

    @JsonIgnore
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Receptionist receptionist;

    public Booking() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(Long numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
