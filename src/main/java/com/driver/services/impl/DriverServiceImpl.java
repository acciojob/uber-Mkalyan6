package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
        Cab cab=new Cab();
		cab.setAvailable(true);
		cab.setPerKmRate(10);
//		cabRepository3.save(cab);



		Driver driver=new Driver();
		driver.setMobile(mobile);
		driver.setPassword(password);
		driver.setCab(cab);


		//Saving to DataBase
		driverRepository3.save(driver);

	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		Optional<Driver> driverOptional=driverRepository3.findById(driverId);
		if(driverOptional.isPresent()){
			Driver driver=driverOptional.get();
			Cab cab= driver.getCab();
			driver.setCab(null);
			cab.setDriver(null);

			// delete both driver and cab
			driverRepository3.delete(driver);
			cabRepository3.delete(cab);
		}

	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable
		Optional<Driver> optionalDriver=driverRepository3.findById(driverId);
		if(optionalDriver.isPresent()){
			Driver driver=optionalDriver.get();
			Cab cab=driver.getCab();
			cab.setAvailable(false);
			cabRepository3.save(cab);

		}

	}
}
