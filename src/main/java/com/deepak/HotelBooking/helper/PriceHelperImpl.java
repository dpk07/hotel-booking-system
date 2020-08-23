package com.deepak.HotelBooking.helper;

import com.deepak.HotelBooking.model.PriceSurge;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Component
public class PriceHelperImpl implements PriceHelper {
    public BigDecimal calculatePrice(List<PriceSurge> priceSurgeList,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     BigDecimal pricePerNight){
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("End Date cannot be before start date");
        }
        BigDecimal totalPrice = new BigDecimal(0);
        //Sort the surge price by end date
        priceSurgeList.sort((a,b)->{
            if(a.getEndDate().isBefore(b.getEndDate()))return -1;
            return 1;
        });
        for(LocalDate date = startDate;date.isBefore(endDate);date = date.plusDays(1)){
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
