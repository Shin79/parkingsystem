package com.parkit.parkingsystem.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, boolean recurringUser){
        if( (ticket.getOutTime() == null) || ticket.getOutTime().isBefore(ticket.getInTime()) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        LocalDateTime inHour = ticket.getInTime();
        LocalDateTime outHour = ticket.getOutTime();
                        
        long diffInOut = ChronoUnit.MINUTES.between(inHour, outHour);  // Calcul de la différence entre heure d'entrée et de sortie
        double duration = ((double)diffInOut / 60);   				  //Transformation en heure 
        
        if(duration <= Fare.FREE_HOURS) {                             // 30 premières minutes gratuites
        	duration = Fare.FREE_PRICE;
        } 
        if(recurringUser) {                                               // Si utilisateur récurrent, calcul du  nouveau tarif
        	duration *= Fare.DISCOUNT_RATE;
        }
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
            	ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

}