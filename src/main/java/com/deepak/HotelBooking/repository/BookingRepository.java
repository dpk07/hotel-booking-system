package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking,Long> {
    @Query("Select b from Booking b where b.hotel.id=:hotelId and (b.startDate >= :startDate and b.startDate <= :endDate) or (b.endDate >= :startDate and b.endDate <= :endDate)")
    List<Booking> findByHotelIdAndDateRange(LocalDate startDate, LocalDate endDate, Long hotelId);

    @Query("Select count(e) from Booking e where e.id=:bookingId and e.hotel.id=:hotelId")
    int countByBookingIdAndHotelId(Long hotelId, Long bookingId);
}
