package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
		return;
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById functionC
		Optional<Customer>optionalCustomer=customerRepository2.findById(customerId);
		if(optionalCustomer.isPresent()){
			Customer customer=optionalCustomer.get();
			customerRepository2.delete(customer);
		}

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		// create TripBooking object and find the driver for the trip from drivers table who is availabe
		List<Driver>driverList=driverRepository2.findAll();
		Optional<Customer>optionalCustomer=customerRepository2.findById(customerId);

		TripBooking tripBooking=new TripBooking();

		if(optionalCustomer.isPresent()){
			Customer customer=optionalCustomer.get();

			// create a tripbooking object
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setDistanceInKm(distanceInKm);
			tripBooking.setCustomer(customer);

			for(Driver driver:driverList){
				if(driver.getCab().isAvailable()){
					tripBooking.setDriver(driver);
					int totalBill=driver.getCab().getPerKmRate()*distanceInKm;
					tripBooking.setBill(totalBill);
					tripBooking.setStatus(TripStatus.CONFIRMED);

					// All attributes are set for trip booking , save to db
					tripBookingRepository2.save(tripBooking);
					return tripBooking;
				}
			}

			if(tripBooking.getDriver()==null){
				throw new Exception("No cab available!");
			}
			customerRepository2.save(customer);

		}
		return tripBooking; // if customer itself is not present with id;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking>optionalTripBooking=tripBookingRepository2.findById(tripId);

		if(optionalTripBooking.isPresent()) {
			TripBooking tripBooking = optionalTripBooking.get();
			tripBooking.setStatus(TripStatus.CANCELED);
			// change the status to competed and update it in db
			tripBookingRepository2.save(tripBooking);
		}

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking>optionalTripBooking=tripBookingRepository2.findById(tripId);
		if(optionalTripBooking.isPresent()){
			TripBooking tripBooking=optionalTripBooking.get();
			tripBooking.setStatus(TripStatus.COMPLETED);
	             // change the status to competed and update it in db
				tripBookingRepository2.save(tripBooking);
			}
		}

	}

