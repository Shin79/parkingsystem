package com.parkit.parkingsystem;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;

public class ParkingSpotDAOTest{
	private static DataBaseConfig dataBaseConfigTest = new DataBaseConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	
	@BeforeAll
	public static void setUp() throws Exception{
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseConfigTest;
				
	}

	//@Test
	//public void getNextAvailableSpotTestForCar() throws SQLException{
	//	int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
	//	System.out.println(result);
	//	assertEquals(result,1);
	//}
	
	@Test
	public void getNextAvailableSpotTestForBike() throws SQLException{
		int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
		System.out.println(result);
		assertEquals(result,4);
	}	
	
	@Test
	public void updateParkingTestIfTrue() throws SQLException{
		
		ParkingSpot parkingSpot = new ParkingSpot(1,ParkingType.CAR,false);
	    boolean isTrue =  parkingSpotDAO.updateParking(parkingSpot);
		assertEquals(isTrue,Boolean.TRUE);
	}
	
	@Test
	public void updateParkingTestIfFalse() throws SQLException{
		
		ParkingSpot parkingSpot = new ParkingSpot(0,ParkingType.CAR,false);
		boolean isFalse =  parkingSpotDAO.updateParking(parkingSpot);
		assertEquals(isFalse,Boolean.FALSE);
		
	}
	

}