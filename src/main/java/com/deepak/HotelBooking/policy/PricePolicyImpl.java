package com.deepak.HotelBooking.policy;

import com.deepak.HotelBooking.helper.PriceHelper;
import com.deepak.HotelBooking.helper.PriceHelperImpl;
import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class PricePolicyImpl implements PricePolicy {
    private final RoomRepository roomRepository;
    private final SurgePriceRepository surgePriceRepository;
    private final PriceHelper priceHelperImpl;
    public PricePolicyImpl(RoomRepository roomRepository,
                           SurgePriceRepository surgePriceRepository,
                           PriceHelperImpl priceHelperImpl) {
        this.roomRepository = roomRepository;
        this.surgePriceRepository = surgePriceRepository;
        this.priceHelperImpl = priceHelperImpl;
    }

    @Override
    public BigDecimal getPrice(Booking booking) {
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        Optional<Room> optionalRoom = roomRepository.findById(booking.getRoom().getId());
        if(optionalRoom.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Could not find room");
        Room room = optionalRoom.get();
        RoomType roomType = room.getRoomType();
        BigDecimal pricePerNight = roomType.getPrice();
        List<PriceSurge> priceSurgeList = surgePriceRepository.findAllByDatesAndRoomType(startDate,
                endDate,
                roomType.getId());
        return priceHelperImpl.calculatePrice(priceSurgeList,startDate,endDate,pricePerNight);
    }

}
