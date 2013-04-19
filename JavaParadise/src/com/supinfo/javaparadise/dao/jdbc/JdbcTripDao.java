package com.supinfo.javaparadise.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.supinfo.javaparadise.dao.DaoFactory;
import com.supinfo.javaparadise.dao.PlaceDao;
import com.supinfo.javaparadise.dao.TripDao;
import com.supinfo.javaparadise.model.Place;
import com.supinfo.javaparadise.model.Trip;

public class JdbcTripDao extends JdbcDao implements TripDao {

	public JdbcTripDao() throws SQLException {
		super();
	}

	@Override
	public Long createTrip(Trip trip) {
		Long result = null;
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"INSERT INTO trips(id, departure_id, destination_id, price) " +
					"VALUES (NULL, ?, ?, ?)"
			, Statement.RETURN_GENERATED_KEYS);
			
			statement.setLong(1, trip.getDeparture().getId());
			statement.setLong(2, trip.getDestination().getId());
			statement.setBigDecimal(3, trip.getPrice());
			
			if(statement.executeUpdate() > 0) {
				ResultSet rskey = statement.getGeneratedKeys();
				if (rskey != null && rskey.next()) {
				  result = rskey.getLong(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Trip findTripById(Long id) {
		Trip result = null;
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"SELECT * FROM trips " +
					"WHERE id = ?"
			);
			
			statement.setLong(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = convertToTrip(statement.executeQuery());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateTrip(Trip trip) {
		boolean success = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"UPDATE trips " +
					"SET 	departure_id = ? ," +
					"		destination_id = ? ," +
					"		price = ?" +
					"WHERE id = ?"
			);
			
			statement.setLong(1, trip.getDeparture().getId());
			statement.setLong(2, trip.getDestination().getId());
			statement.setBigDecimal(3, trip.getPrice());
			statement.setLong(4, trip.getId());
			
			if(statement.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean removeTrip(Trip trip) {
		boolean success = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"DELETE FROM trips WHERE id = ?"
			);
			
			statement.setLong(1, trip.getId());
			
			if(statement.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public List<Trip> findTripByDeparture(Place place) {
		List<Trip> results = new ArrayList<Trip>();
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"SELECT * FROM trips WHERE departure_id = ?"
			);
			
			statement.setLong(1, place.getId());
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				results.add(convertToTrip(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<Trip> findTripByDestination(Place place) {
		List<Trip> results = new ArrayList<Trip>();
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"SELECT * FROM trips WHERE destination_id = ?"
			);
			
			statement.setLong(1, place.getId());
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				results.add(convertToTrip(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private Trip convertToTrip(ResultSet resultSet) throws SQLException {
		Trip trip = new Trip();
		trip.setId(resultSet.getLong("id"));
		trip.setPrice(resultSet.getBigDecimal("price"));
		
		PlaceDao placeDao = DaoFactory.getPlaceDao();
		
		long departureId = resultSet.getLong("departure_id");
		Place departure = placeDao.findPlaceById(departureId);
		trip.setDeparture(departure);
		
		long destinationId = resultSet.getLong("destination_id");
		Place destination = placeDao.findPlaceById(destinationId);
		trip.setDeparture(destination);
		
		return trip;
	}

}
