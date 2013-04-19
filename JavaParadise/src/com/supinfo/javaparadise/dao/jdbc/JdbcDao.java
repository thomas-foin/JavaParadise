package com.supinfo.javaparadise.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public abstract class JdbcDao {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/JavaParadise";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	
	static {
		try {
			Class.forName(Driver.class.getName());
		} catch (ClassNotFoundException e) {
			System.exit(-1);
		}
	}
	
	private Connection connection;
	
	
	public JdbcDao() throws SQLException {
		this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
	
	protected Connection getConnection() {
		return connection;
	}

}
