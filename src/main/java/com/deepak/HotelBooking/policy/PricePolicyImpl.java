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

    /**
     * Calculates the total price for the provided booking
     * <p>Fetches the room price details and the surge price details from the database</p>
     * <p>Then calculates the total price.</p>
     * @param booking Booking object for which the price needs to be calculated.
     * @return Total price for the provided booking.
     */
    @Override
    public BigDecimal getPrice(Booking booking) throws IllegalArgumentException {
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("End Date cannot be before start date");
        }
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
