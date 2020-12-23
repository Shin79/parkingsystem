package com.parkit.parkingsystem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class TicketDAOTest{
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
	
	@BeforeAll
	public static void setUp() throws Exception{
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();
	}
	@Test
	public void saveTicketTest() {
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1,ParkingType.CAR,true);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0);
		LocalDateTime inTime = LocalDateTime.now().minusHours(1);
		ticket.setInTime(inTime);
		LocalDateTime outTime = LocalDateTime.now();
		ticket.setOutTime(outTime);
		boolean isFalse = ticketDAO.saveTicket(ticket);
		System.out.println(isFalse);
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
	@Test
	public void updateTicketTest() {
		Ticket ticket = new Ticket();
		ticket.setPrice(Fare.CAR_RATE_PER_HOUR);
		LocalDateTime outTime  = LocalDateTime.now();
		ticket.setOutTime(outTime);
		boolean isTrue = ticketDAO.updateTicket(ticket);
		assertEquals(isTrue,Boolean.TRUE);
	}
	@Test
	public void isRecurringUserTest() {
		Ticket ticket = new Ticket();
		ticket.setVehicleRegNumber("ABCDEF");
		ParkingSpot parkingSpot = new ParkingSpot(1,ParkingType.CAR,true);
		ticket.setParkingSpot(parkingSpot);
		ticket.setPrice(0);
		LocalDateTime inTime = LocalDateTime.now().minusHours(1);
		ticket.setInTime(inTime);
		LocalDateTime outTime = LocalDateTime.now();
		ticket.setOutTime(outTime);
		ticketDAO.saveTicket(ticket);
		ticketDAO.saveTicket(ticket);
		boolean isTrue = ticketDAO.isRecurringUser(ticket.getVehicleRegNumber());
		assertTrue(isTrue);
	}
}