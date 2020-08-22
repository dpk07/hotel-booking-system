package com.deepak.HotelBooking.repository;

import com.deepak.HotelBooking.model.Receptionist;
import org.springframework.data.repository.CrudRepository;

public interface ReceptionistRepository extends CrudRepository<Receptionist,Long> {
    Receptionist findByUserName(String userName);
}
