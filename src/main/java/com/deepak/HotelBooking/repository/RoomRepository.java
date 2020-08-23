package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room,Long> {
    @Query("Select e from Room e where e.roomType.id=:roomTypeId and e.id not in" +
            "(select b.room.id from Booking b where (b.startDate>=:startDate and b.startDate<=:endDate) or" +
            "(b.endDate>=:startDate and b.endDate<=:endDate))")
    List<Room> findByRoomTypeIdAndDateRange(Long roomTypeId, LocalDate startDate, LocalDate endDate);

    @Query("Select e from Room e where e.id=:roomId and e.id not in" +
            "(select b.room.id from Booking b where (b.startDate>=:startDate and b.startDate<=:endDate) or" +
            "(b.endDate>=:startDate and b.endDate<=:endDate))")
    Optional<Room> findByRoomIdRoomIdAndDateRange(Long roomId, LocalDate startDate, LocalDate endDate);

    @Query("Select count(e) from Room e where e.id=:roomId and e.hotel.id=:hotelId")
    int countByRoomIdAndHotelId(Long hotelId, Long roomId);

}
