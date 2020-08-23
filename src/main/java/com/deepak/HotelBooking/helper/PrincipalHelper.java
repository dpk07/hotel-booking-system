package com.deepak.HotelBooking.helper;

import com.deepak.HotelBooking.model.Receptionist;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalHelper {
    /**
     * Gets the Hotel Id of the logged in User
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

    /**
     * Gets the User Id of the logged in User
     * @return User Id from the USer Principal
     */
    public Long getUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId=null;
        if (principal instanceof Receptionist) {
            userId = ((Receptionist)principal).getId();
        }
        return userId;
    }
}
