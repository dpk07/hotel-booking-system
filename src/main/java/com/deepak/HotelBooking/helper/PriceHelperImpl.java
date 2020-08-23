package com.deepak.HotelBooking.helper;

import com.deepak.HotelBooking.model.PriceSurge;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Component
public class PriceHelperImpl implements PriceHelper {


    /**
     * Calculates the total price for the given interval.
     *  <p>
     *      If there are multiple surge prices for a single day,
     *      the one with the shorter duration is considered.
     *  <p/>
     *  <p>
     *      If there are multiple surge prices for a single day,
     *      with the same durations then the one ending later is considered.
     *  </p>
     * @param priceSurgeList List of price surges between the provided booking interval.
     * @param startDate Start date of the booking.
     * @param endDate End date of the booking.
     * @param pricePerNight Default price for each night.
     * @return  Total price for the given interval.
     * @throws IllegalArgumentException If the startDate is same or after endDate
     */
    public BigDecimal calculatePrice(List<PriceSurge> priceSurgeList,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     BigDecimal pricePerNight) throws IllegalArgumentException{
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("End Date cannot be before start date");
        }
        BigDecimal totalPrice = new BigDecimal(0);
        //Sort the surge price by end date
        priceSurgeList.sort((a,b)->{
            if(a.getEndDate().isBefore(b.getEndDate()))return -1;
            return 1;
        });
        //Iterates through each day in the interval and calculates the proper price for that day
        for(LocalDate date = startDate;date.isBefore(endDate);date = date.plusDays(1)){
            BigDecimal priceForThatDay = pricePerNight;
            Long surgeDaysBetween = Long.MAX_VALUE;
            //Iterates through the price surges in the given interval
            for (PriceSurge priceSurge : priceSurgeList) {
                LocalDate surgeStartDate = priceSurge.getStartDate();
                LocalDate surgeEndDate = priceSurge.getEndDate();
                //Duration of days in the current price surge
                Long currentSurgeDaysBetween = ChronoUnit.DAYS.between(surgeStartDate, surgeEndDate);

                if (date.isEqual(surgeStartDate) ||
                        date.isEqual(surgeEndDate) ||
                        (date.isAfter(surgeStartDate) &&
                                date.isBefore(surgeEndDate))) {
                    //Set the price of the lowest surge duration or
                    //if the duration are same set the one which ends later
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
