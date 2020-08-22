package com.deepak.HotelBooking.configuration;

import com.deepak.HotelBooking.model.Receptionist;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HotelIdHelper {
    /**
     * @return Hotel Id from the User Principal
     */
    public Long getHotelId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long hotelId=null;
        if (principal instanceof Receptionist) {
            hotelId = ((Receptionist)principal).getHotel().getId();
        }
        return hotelId;
    }
}
