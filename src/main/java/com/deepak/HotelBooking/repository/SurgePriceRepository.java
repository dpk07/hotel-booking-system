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
    @Query("Select p from PriceSurge p where (p.startDate<=:startDate and p.endDate>=:startDate) or" +
            "(p.startDate<=:endDate and p.endDate>=:endDate) or (p.startDate>=:startDate and p.endDate<=:endDate)" +
            " and p.roomType.id=:roomTypeId")
    List<PriceSurge> findAllByDatesAndRoomType(LocalDate startDate, LocalDate endDate, Long roomTypeId);

    @Query("Select count(e) from RoomType e where e.hotel.id=:hotelId and e.id=(Select b.roomType.id from PriceSurge b where b.id=:surgeId)")
    int countBySurgeIdAndHotelId(Long hotelId,Long surgeId);
}
