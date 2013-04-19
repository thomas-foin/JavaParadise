package com.supinfo.javaparadise.dao;

import java.util.List;

import com.supinfo.javaparadise.model.Place;
import com.supinfo.javaparadise.model.Trip;

public interface TripDao {
	
	Long createTrip(Trip trip);
	Trip findTripById(Long id);
	boolean updateTrip(Trip trip);
	boolean removeTrip(Trip trip);
	List<Trip> findTripByDeparture(Place place);
	List<Trip> findTripByDestination(Place place);

}
