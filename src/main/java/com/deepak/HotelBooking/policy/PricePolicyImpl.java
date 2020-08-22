package com.deepak.HotelBooking.policy;

import com.deepak.HotelBooking.model.Booking;
import com.deepak.HotelBooking.model.PriceSurge;
import com.deepak.HotelBooking.model.Room;
import com.deepak.HotelBooking.model.RoomType;
import com.deepak.HotelBooking.repository.BookingRepository;
import com.deepak.HotelBooking.repository.RoomRepository;
import com.deepak.HotelBooking.repository.RoomTypeRepository;
import com.deepak.HotelBooking.repository.SurgePriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PricePolicyImpl implements PricePolicy {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final BookingRepository bookingRepository;
    private final SurgePriceRepository surgePriceRepository;

    public PricePolicyImpl() {
        bookingRepository = null;
        roomTypeRepository = null;
        surgePriceRepository = null;
        roomRepository = null;
    }

    public PricePolicyImpl(RoomRepository roomRepository,
                           RoomTypeRepository roomTypeRepository,
                           BookingRepository bookingRepository,
                           SurgePriceRepository surgePriceRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.bookingRepository = bookingRepository;
        this.surgePriceRepository = surgePriceRepository;
    }

    @Override
    public BigDecimal getPrice(Booking booking) {
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        Optional<Room> optionalRoom = roomRepository.findById(booking.getRoom().getId());
        if(!optionalRoom.isPresent())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not find room");
        Room room = optionalRoom.get();
        RoomType roomType = room.getRoomType();
        BigDecimal pricePerNight = roomType.getPrice();
        BigDecimal totalPrice = new BigDecimal(0);
        List<PriceSurge> priceSurgeList = surgePriceRepository.findAllByDatesAndRoomType(startDate,
                endDate,
                roomType.getId());
        return calculatePrice(priceSurgeList,startDate,endDate,pricePerNight);
    }
    public BigDecimal calculatePrice(List<PriceSurge> priceSurgeList,
                                      LocalDate startDate,
                                      LocalDate endDate,
                                      BigDecimal pricePerNight){
        BigDecimal totalPrice = new BigDecimal(0);
        //Sort the surge price by end date
        priceSurgeList.sort((a,b)->{
            if(a.getEndDate().isBefore(b.getEndDate()))return -1;
            return 1;
        });
        for(LocalDate date = startDate;date.isBefore(endDate.plusDays(1));date = date.plusDays(1)){
            BigDecimal priceForThatDay = pricePerNight;
            Long surgeDaysBetween = Long.MAX_VALUE;
            for (PriceSurge priceSurge : priceSurgeList) {
                LocalDate surgeStartDate = priceSurge.getStartDate();
                LocalDate surgeEndDate = priceSurge.getEndDate();
                Long currentSurgeDaysBetween = ChronoUnit.DAYS.between(surgeStartDate, surgeEndDate);
                if (date.isEqual(surgeStartDate) ||
                        date.isEqual(surgeEndDate) ||
                        (date.isAfter(surgeStartDate) &&
                                date.isBefore(surgeEndDate))) {
                    if(currentSurgeDaysBetween<=surgeDaysBetween){
                        priceForThatDay = priceSurge.getPrice();
                        surgeDaysBetween =currentSurgeDaysBetween;
                    }
                }
            }
            totalPrice = totalPrice.add(priceForThatDay);
        }

        return totalPrice;
    }
}
