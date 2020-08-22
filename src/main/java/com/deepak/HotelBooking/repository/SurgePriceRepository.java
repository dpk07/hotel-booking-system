package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SurgePriceRepository extends CrudRepository<PriceSurge,Long> {
    @Query("Select p from PriceSurge p where p.startDate>=:startDate and p.endDate<=:endDate and p.roomType.id=:roomTypeId")
    List<PriceSurge> findAllByDatesAndRoomType(LocalDate startDate, LocalDate endDate, Long roomTypeId);
}
