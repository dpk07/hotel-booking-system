package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SurgeSecurityService {
    private final RoomTypeRepository roomTypeRepository;
    private final SurgePriceRepository surgePriceRepository;
    public SurgeSecurityService(RoomTypeRepository roomTypeRepository, SurgePriceRepository surgePriceRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.surgePriceRepository = surgePriceRepository;
    }

    public boolean hasAccessToEditSurge(PriceSurge priceSurge) {
        Long hotelId = getHotelId();
        int count = surgePriceRepository.countBySurgeIdAndHotelId(hotelId,priceSurge.getId());
        return count==1;
    }

    public boolean hasAccessToCreateSurge(Long roomTypeId) {
        Long hotelId = getHotelId();
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomTypeId);
        return count==1;
    }

    private Long getHotelId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long hotelId=null;
        if (principal instanceof Receptionist) {
            hotelId = ((Receptionist)principal).getHotel().getId();
        }
        return hotelId;
    }
}
