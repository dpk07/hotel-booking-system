package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class SurgeService {
    private final SurgePriceRepository surgePriceRepository;

    public SurgeService(SurgePriceRepository surgePriceRepository) {
        this.surgePriceRepository = surgePriceRepository;
    }
    @PreAuthorize("@surgeSecurityService.hasAccessToCreateSurge(#roomTypeId)")
    public PriceSurge addSurgePrice(PriceSurge priceSurge, Long roomTypeId){
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        priceSurge.setRoomType(roomType);
        return surgePriceRepository.save(priceSurge);
    }
    @PreAuthorize("@surgeSecurityService.hasAccessToEditSurge(#priceSurge)")
    public PriceSurge editSurgePrice(PriceSurge priceSurge) {
        if(priceSurge.getId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Please provide Surge Id");
        Optional<PriceSurge> prevObject = surgePriceRepository.findById(priceSurge.getId());
        if(!prevObject.isPresent()){
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
}
