package com.parkit.parkingsystem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class TicketDAOTest{
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static TicketDAO ticketDAO;
	
	@BeforeAll
	public static void setUp() throws Exception{
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
	}
	@Test
	public void saveTicketTest() {
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1,ParkingType.CAR,false);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(Fare.CAR_RATE_PER_HOUR);
		LocalDateTime inTime = LocalDateTime.now();
		ticket.setInTime(inTime);
		boolean isFalse = ticketDAO.saveTicket(ticket);
		assertEquals(isFalse,Boolean.FALSE);
		
	}
	@Test
	public void getTicketTest() {
		String vehicleRegNumber = "ABCDEF";
		Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
		assertNotNull(ticket.getId());
		assertNotNull(ticket.getInTime());
		assertNotNull(ticket.getParkingSpot());
		assertNotNull(ticket.getVehicleRegNumber());
	}
}