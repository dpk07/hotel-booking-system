package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.RoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType,Long> {
    List<RoomType> findAllByHotelId(Long id);
    int countAllByHotelId(Long id);
}
