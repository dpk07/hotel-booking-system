package com.deepak.HotelBooking.controller;

import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.service.RoomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping("{roomTypeId}/add")
    public Room addRoom(@RequestBody Room room, @PathVariable String roomTypeId){
        return roomService.addRoom(room,Long.parseLong(roomTypeId));
    }

    @PutMapping
    public Room editRoom(@RequestBody Room room){
        return roomService.editRoom(room);
    }

    @DeleteMapping
    public void deleteRoom(@RequestBody Room room){
        roomService.deleteRoom(room);
    }

}
