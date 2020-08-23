package com.deepak.HotelBooking.service.security;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SurgeSecurityService {
    private final RoomTypeRepository roomTypeRepository;
    private final SurgePriceRepository surgePriceRepository;
    private final PrincipalHelper principalHelper;
    public SurgeSecurityService(RoomTypeRepository roomTypeRepository,
                                SurgePriceRepository surgePriceRepository,
                                PrincipalHelper principalHelper) {
        this.roomTypeRepository = roomTypeRepository;
        this.surgePriceRepository = surgePriceRepository;
        this.principalHelper = principalHelper;
    }
    /**
     * Checks if this surge price exists and if the user is authorized to edit it.
     *
     * <p>This method checks if roomTypeId is associated to the users Hotel Id.</p>
     * @param priceSurge Price Surge object that the user wants to create.
     * @return true if the user has access to edit this surge price
     */
    public boolean hasAccessToEditSurge(PriceSurge priceSurge) {
        Long hotelId = principalHelper.getHotelId();
        if(priceSurge.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Surge Price ID required to edit surge price.");
        }
        int count = surgePriceRepository.countBySurgeIdAndHotelId(hotelId,priceSurge.getId());
        return count==1;
    }

    /**
     * Checks if the user has access to create this surge price.
     *
     * <p>This method checks if roomTypeId is associated to the users Hotel Id.</p>
     * @param roomTypeId Room Type Id of the room type for which the user wants to create a surge
     * @return true if the user has access to create this surge price
     */
    public boolean hasAccessToCreateSurge(Long roomTypeId) {
        Long hotelId = principalHelper.getHotelId();
        int count = roomTypeRepository.countByRoomTypeIdAndHotelId(hotelId,roomTypeId);
        return count==1;
    }


}
