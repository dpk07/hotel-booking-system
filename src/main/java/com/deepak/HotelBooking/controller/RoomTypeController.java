package com.deepak.HotelBooking.controller;

import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.service.RoomTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roomType")
public class RoomTypeController {
    private RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping("{hotelId}/add")
    public RoomType addRoomType(@RequestBody RoomType roomType, @PathVariable String hotelId){
        return roomTypeService.addRoomType(roomType,Long.parseLong(hotelId));
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
        return roomTypeService.getAllRoomTypes();
    }
}
