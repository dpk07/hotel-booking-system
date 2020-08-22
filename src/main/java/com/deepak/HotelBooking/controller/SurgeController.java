package com.deepak.HotelBooking.controller;

import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.service.SurgeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/surge")
public class SurgeController {
    private final SurgeService surgeService;

    public SurgeController(SurgeService surgeService) {
        this.surgeService = surgeService;
    }
    @PostMapping("{roomTypeId}/add")
    public PriceSurge addSurge(@Valid @RequestBody PriceSurge priceSurge, @PathVariable String roomTypeId){
        return surgeService.addSurgePrice(priceSurge,Long.parseLong(roomTypeId));
    }
    @PutMapping
    public PriceSurge editSurge(@RequestBody PriceSurge priceSurge){
        return surgeService.editSurgePrice(priceSurge);
    }
    @DeleteMapping
    public void deleteSurge(@RequestBody PriceSurge priceSurge){
        surgeService.deleteSurgePrice(priceSurge);
    }
}
