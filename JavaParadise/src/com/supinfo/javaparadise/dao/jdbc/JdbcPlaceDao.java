package com.supinfo.javaparadise.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.supinfo.javaparadise.dao.PlaceDao;
import com.supinfo.javaparadise.model.Place;

public class JdbcPlaceDao extends JdbcDao implements PlaceDao {

	public JdbcPlaceDao() throws SQLException {
		super();
	}

	@Override
	public Long createPlace(Place place) {
		Long result = null;
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"INSERT INTO places (id, name)" +
					"VALUES (NULL ,?)"
			, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, place.getName());
			
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
	public Place findPlaceById(Long id) {
		Place result = null;
		try {
			PreparedStatement statement = getConnection().prepareStatement(
					"SELECT * FROM places " +
					"WHERE id = ?"
			);
			
			statement.setLong(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = convertToPlace(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updatePlace(Place place) {
		boolean success = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement("UPDATE places SET name = ? WHERE id = ?");
			statement.setString(1, place.getName());
			statement.setLong(2, place.getId());
			if(statement.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean removePlace(Place place) {
		boolean success = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement("DELETE FROM places WHERE id = ?");
			statement.setLong(1, place.getId());
			if(statement.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	private Place convertToPlace(ResultSet resultSet) throws SQLException {
		Place place = new Place();
		place.setId(resultSet.getLong("id"));
		place.setName(resultSet.getString("name"));
		return place;
	}

}
