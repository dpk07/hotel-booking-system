package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.DateRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking,Long> {
    @Query("Select b from Booking b where (b.startDate >= :startDate and b.startDate <= :endDate) or (b.endDate >= :startDate and b.endDate <= :endDate)")
    List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate);
}
