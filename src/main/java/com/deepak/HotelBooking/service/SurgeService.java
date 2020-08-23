package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.helper.PrincipalHelper;
import com.deepak.HotelBooking.model.*;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SurgeService {
    private final SurgePriceRepository surgePriceRepository;
    private final PrincipalHelper principalHelper;
    public SurgeService(SurgePriceRepository surgePriceRepository, PrincipalHelper principalHelper) {
        this.surgePriceRepository = surgePriceRepository;
        this.principalHelper = principalHelper;
    }
    @PreAuthorize("@surgeSecurityService.hasAccessToCreateSurge(#roomTypeId)")
    public PriceSurge addSurgePrice(PriceSurge priceSurge, Long roomTypeId){
        checkDateRange(priceSurge);
        Long hotelId = principalHelper.getHotelId();
        priceSurge.setRoomType(new RoomType(roomTypeId));
        priceSurge.setHotel(new Hotel(hotelId));
        return surgePriceRepository.save(priceSurge);
    }

    @PreAuthorize("@surgeSecurityService.hasAccessToEditSurge(#priceSurge)")
    public PriceSurge editSurgePrice(PriceSurge priceSurge) {
        checkDateRange(priceSurge);
        if(priceSurge.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Surge Id");
        Optional<PriceSurge> prevObject = surgePriceRepository.findById(priceSurge.getId());
        if(prevObject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Price Surge you are trying to edit is not found");
        }
        PriceSurge prevSurge = prevObject.get();
        if(priceSurge.getPrice()!=null){
            prevSurge.setPrice(priceSurge.getPrice());
        }
        if(priceSurge.getStartDate()!=null){
            prevSurge.setStartDate(priceSurge.getStartDate());
        }
        if(priceSurge.getEndDate()!=null){
            prevSurge.setEndDate(priceSurge.getEndDate());
        }
        if(priceSurge.getRoomType()!=null && priceSurge.getRoomType().getId()!=null){
            prevSurge.setRoomType(priceSurge.getRoomType());
        }
        return surgePriceRepository.save(prevSurge);
    }

    @PreAuthorize("@surgeSecurityService.hasAccessToEditSurge(#priceSurge)")
    public void deleteSurgePrice(PriceSurge priceSurge) {
        if(priceSurge.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Surge Id");
        surgePriceRepository.delete(priceSurge);
    }

    /**
     * Checks if the given date range is valid.
     * @param priceSurge Price Surge Object which needs to be checked.
     * @throws ResponseStatusException If the endDate is before startDate
     */
    private void checkDateRange(PriceSurge priceSurge) throws ResponseStatusException{
        LocalDate startDate = priceSurge.getStartDate();
        LocalDate endDate = priceSurge.getEndDate();
        if(endDate.isBefore(startDate)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "End Date cannot be before Start Date");
        }
    }
}
