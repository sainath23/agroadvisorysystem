package com.doitgeek.AgroAdvisorySystem.dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

	/* Following method will create DB Connection */
	/*@SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(Constants.dbClass);
			con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);
		} catch (Exception e) {
			throw e;
		} finally {
			return con;
		}
	}*/
	
	public static Connection getConnection() throws URISyntaxException, SQLException { 
		String dbUrl = System.getenv("JDBC_DATABASE_URL"); return
		DriverManager.getConnection(dbUrl); 
	  }
	 

	/* Method to check whether username and password combination is correct */
	public static boolean checkLogin(String username, String password) throws Exception {
		boolean isUserAvailable = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement sqlStatement = dbConn.createStatement();
			String query = "SELECT * FROM userinfo WHERE user_name = '" + username + "' AND user_password=" + "'"
					+ password + "'";
			ResultSet rs = sqlStatement.executeQuery(query);
			while (rs.next()) {
				isUserAvailable = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isUserAvailable;
	}

	/* Method to insert registration details */
	public static boolean insertUser(String firstname, String lastname, long mobileno, String username, String password,
			String district, String taluka) throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into userinfo(first_name, last_name, mobile_no, "
					+ "user_name, user_password, district, taluka) values('" + firstname + "'," + "'" + lastname + "','"
					+ mobileno + "','" + username + "','" + password + "'," + "'" + district + "','" + taluka + "')";
			int records = stmt.executeUpdate(query);
			if (records > 0) {
				insertStatus = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	/* Method to update details */
	public static boolean updateUser(String password, String district, String taluka, 
										String username) throws SQLException, Exception {
		boolean updateStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "UPDATE userinfo SET user_password = '"+password+"', district = '"+district+"', "
													+ "taluka = '"+taluka+"' WHERE user_name = '"+username+"'";
			int records = stmt.executeUpdate(query);
			if (records > 0) {
				updateStatus = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return updateStatus;
	}
}
