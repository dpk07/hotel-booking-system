package com.deepak.HotelBooking.controller;

import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.service.RoomTypeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/roomType")
public class RoomTypeController {
    private RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping
    public RoomType addRoomType(@RequestBody RoomType roomType){
        return roomTypeService.addRoomType(roomType,1L);
    }

    @PutMapping
    public RoomType editRoomType(@RequestBody RoomType roomType){
        return roomTypeService.editRoomType(roomType);
    }

    @DeleteMapping
    public void deleteRoomType(@RequestBody RoomType roomType){
        roomTypeService.deleteRoomType(roomType);
    }
    @GetMapping
    public List<RoomType> getAllRoomTypes(){
        return roomTypeService.getAllRoomTypesByHotelId(1L);
    }

    @PostMapping("/date")
    public List<RoomType> getAllRoomTypesByDateRange(RoomType roomType){
        return new ArrayList<RoomType>();
    }
}
