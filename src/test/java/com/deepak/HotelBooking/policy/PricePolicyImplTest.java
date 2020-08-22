package com.deepak.HotelBooking.policy;

import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricePolicyImplTest {
    PricePolicyImpl pricePolicyImpl= new PricePolicyImpl();
    List<PriceSurge> priceSurgeList = null;
    @BeforeEach
    void setupPrice(){


        PriceSurge one = new PriceSurge(new RoomType(),LocalDate.of(2020,2,1),
                LocalDate.of(2020,2,28),new BigDecimal("3500"));
        PriceSurge two = new PriceSurge(new RoomType(),LocalDate.of(2020,2,10),
                LocalDate.of(2020,2,17),new BigDecimal("4000"));
        PriceSurge three = new PriceSurge(new RoomType(),LocalDate.of(2020,2,14),
                LocalDate.of(2020,2,21),new BigDecimal("6000"));
        PriceSurge four = new PriceSurge(new RoomType(),LocalDate.of(2020,2,15),
                LocalDate.of(2020,2,23),new BigDecimal("4200"));
        PriceSurge five = new PriceSurge(new RoomType(),LocalDate.of(2020,2,20),
                LocalDate.of(2020,2,25),new BigDecimal("4100"));
        priceSurgeList = new ArrayList<>();
        priceSurgeList.add(one);
        priceSurgeList.add(two);
        priceSurgeList.add(three);
        priceSurgeList.add(four);
        priceSurgeList.add(five);
    }

    @Test
    void calculatePrice1() {
        LocalDate startDate = LocalDate.of(2020,2,14);
        LocalDate endDate = LocalDate.of(2020,2,16);
        BigDecimal expected = new BigDecimal("18000");
        BigDecimal actual =  pricePolicyImpl.calculatePrice(priceSurgeList,startDate,endDate,new BigDecimal("3000"));
        System.out.println(actual);
        assertEquals(expected,actual);
    }
}