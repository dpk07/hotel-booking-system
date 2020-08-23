package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.configuration.HotelIdHelper;
import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SurgeSecurityService {
    private final RoomTypeRepository roomTypeRepository;
    private final SurgePriceRepository surgePriceRepository;
    private final HotelIdHelper hotelIdHelper;
    public SurgeSecurityService(RoomTypeRepository roomTypeRepository,
                                SurgePriceRepository surgePriceRepository,
                                HotelIdHelper hotelIdHelper) {
        this.roomTypeRepository = roomTypeRepository;
        this.surgePriceRepository = surgePriceRepository;
        this.hotelIdHelper = hotelIdHelper;
    }

    public boolean hasAccessToEditSurge(PriceSurge priceSurge) {
        Long hotelId = hotelIdHelper.getHotelId();
        if(priceSurge.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Surge Price ID required to edit surge price.");
        }
        int count = surgePriceRepository.countBySurgeIdAndHotelId(hotelId,priceSurge.getId());
        return count==1;
    }

    public boolean hasAccessToCreateSurge(Long roomTypeId) {
        Long hotelId = hotelIdHelper.getHotelId();
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomTypeId);
        return count==1;
    }


}
