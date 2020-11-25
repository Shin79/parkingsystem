package com.parkit.parkingsystem;

import java.time.LocalDateTime;
import java.time.Month;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar(){
        LocalDateTime inTime = LocalDateTime.of(2020, Month.DECEMBER,01,10,05,32);
        // inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2020,Month.DECEMBER,01,17,34,22);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareBike(){
        LocalDateTime inTime = LocalDateTime.of(2020, Month.DECEMBER,01,10,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2020, Month.DECEMBER,01,17,34,22);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareUnkownType(){
        LocalDateTime inTime = LocalDateTime.of(2020,Month.DECEMBER,01,10,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2020,Month.DECEMBER,01,17,34,22);
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        LocalDateTime inTime = LocalDateTime.of(2020,Month.DECEMBER,01,18,05,32);
        //inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2020,Month.DECEMBER,01,17,34,22);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        LocalDateTime inTime = LocalDateTime.of(2020,Month.DECEMBER,01,18,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        LocalDateTime outTime = LocalDateTime.of(2020,Month.DECEMBER,01,18,45,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        LocalDateTime inTime = LocalDateTime.of(2020,Month.DECEMBER,01,18,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        LocalDateTime outTime = LocalDateTime.of(2020,Month.DECEMBER,01,18,45,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        LocalDateTime inTime = LocalDateTime.of(2020,Month.DECEMBER,01,18,45,32);
        //inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        LocalDateTime outTime = LocalDateTime.of(2020,Month.DECEMBER,02,18,45,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

}