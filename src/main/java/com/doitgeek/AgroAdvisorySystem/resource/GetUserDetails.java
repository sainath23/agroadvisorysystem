package com.doitgeek.AgroAdvisorySystem.resource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.doitgeek.AgroAdvisorySystem.dao.DbConnection;
import com.doitgeek.AgroAdvisorySystem.model.Utility;

@Path("user")
public class GetUserDetails {

	@GET
	@Path("user_details")
	@Produces(MediaType.APPLICATION_JSON)
	public String retrieveUserDetails(@QueryParam("username") String username) 
										throws SQLException, Exception {
		String response = "";
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.getConnection();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Statement sqlStatement = dbConn.createStatement();
			String query = "SELECT * FROM userinfo WHERE user_name = '"+username+"'";
			ResultSet rs = sqlStatement.executeQuery(query);
		
			if(rs.next()) {
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String mobile_no = rs.getString("mobile_no");
				String user_name = rs.getString("user_name");
				String user_password = rs.getString("user_password");
				String user_district = rs.getString("district");
				String user_taluka = rs.getString("taluka");
				response = Utility.constructJSONForSelectQuery(new String[] {first_name, last_name, mobile_no, 
																		user_name, user_password, user_district, 
																		user_taluka});
			}else {
				response = "Error has been occurred!";
			}
		} catch(SQLException sqle) {
			throw sqle;
		} catch(Exception e) {
			if(dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if(dbConn != null) {
				dbConn.close();
			}
		}
		
		return response;
	}
}
