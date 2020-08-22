package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType,Long> {
    List<RoomType> findAllByHotelId(Long id);
    int countAllByHotelId(Long id);
    @Query("Select count(e) from RoomType e where e.id=:roomTypeId and e.hotel.id=:hotelId")
    int countByRoomTypeIdAndHotelId(Long hotelId, Long roomTypeId);
}
