package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room,Long> {
    final String query="Select * from Room where RoomType.id=:roomType.id";
//    @Query(query)
    List<Room> findByRoomTypeId(RoomType roomType);
    @Query("Select count(e) from RoomType e where e.hotel.id=:hotelId and e.id=(Select b.roomType.id from Room b where b.id=:roomId)")
    int countByRoomTypeIdAndHotelId(Long hotelId,Long roomId);

}
