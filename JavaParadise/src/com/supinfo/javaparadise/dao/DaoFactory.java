package com.supinfo.javaparadise.dao;

import java.sql.SQLException;

import com.supinfo.javaparadise.dao.jdbc.JdbcPlaceDao;
import com.supinfo.javaparadise.dao.jdbc.JdbcTripDao;

public class DaoFactory {
	
	private static PlaceDao placeDao;
	private static TripDao tripDao;
	
	
	private DaoFactory() {
		throw new IllegalStateException();
	}
	
	public static PlaceDao getPlaceDao() throws SQLException {
		if(placeDao == null) {
			placeDao = new JdbcPlaceDao();
		}
		return placeDao;
	}
	
	public static TripDao getTripDao() throws SQLException {
		if(tripDao == null) {
			tripDao = new JdbcTripDao();
		}
		return tripDao;
	}

}
