package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room,Long> {
    String query="Select * from Room where RoomType.id=:roomType.id";
//    @Query(query)
//    List<Room> findByRoomTypeId(RoomType roomType);
    @Query("Select count(e) from Room e where e.id=:roomId and e.hotel.id=:hotelId")
    int countByRoomIdAndHotelId(Long hotelId, Long roomId);

}
