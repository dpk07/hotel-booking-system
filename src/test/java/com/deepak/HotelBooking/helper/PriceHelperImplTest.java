package com.deepak.HotelBooking.helper;

import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceHelperImplTest {
    PriceHelper priceHelperImpl = new PriceHelperImpl();
    List<PriceSurge> priceSurgeList = null;
    @BeforeEach
    void setupPrice(){
        PriceSurge one = new PriceSurge(new RoomType(), LocalDate.of(2020,2,1),
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
    void calculatePrice_ProperDate1_ReturnsProperResult() {
        LocalDate startDate = LocalDate.of(2020,2,14);
        LocalDate endDate = LocalDate.of(2020,2,16);
        BigDecimal expected = new BigDecimal("12000");
        BigDecimal actual =  priceHelperImpl.calculatePrice(priceSurgeList,startDate,endDate,new BigDecimal("3000"));
        assertEquals(expected,actual);
    }
    @Test
    void calculatePrice_ProperDate2_ReturnsProperResult() {
        LocalDate startDate = LocalDate.of(2020,2,20);
        LocalDate endDate = LocalDate.of(2020,2,23);
        BigDecimal expected = new BigDecimal("12300");
        BigDecimal actual =  priceHelperImpl.calculatePrice(priceSurgeList,startDate,endDate,new BigDecimal("3000"));
        assertEquals(expected,actual);
    }
    @Test
    void calculatePrice_ImproperDateGiven_ThrowsIllegalArgumentException() {
        LocalDate startDate = LocalDate.of(2020,2,20);
        LocalDate endDate = LocalDate.of(2020,2,18);
        Exception e = assertThrows(IllegalArgumentException.class,()-> priceHelperImpl.calculatePrice(priceSurgeList,startDate,endDate,new BigDecimal("3000")));
        assertEquals(e.getMessage(),"End Date cannot be before start date");
    }
    @Test
    void calculatePrice_ProperDate3_ReturnsProperResult() {
        LocalDate startDate = LocalDate.of(2020,2,19);
        LocalDate endDate = LocalDate.of(2020,2,22);
        BigDecimal expected = new BigDecimal("14200");
        BigDecimal actual =  priceHelperImpl.calculatePrice(priceSurgeList,startDate,endDate,new BigDecimal("3000"));
        assertEquals(expected,actual);
    }
}