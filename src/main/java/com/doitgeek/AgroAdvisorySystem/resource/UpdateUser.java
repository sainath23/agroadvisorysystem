package com.doitgeek.AgroAdvisorySystem.resource;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.doitgeek.AgroAdvisorySystem.dao.DbConnection;
import com.doitgeek.AgroAdvisorySystem.model.Utility;

@Path("/user")
public class UpdateUser {
	
	@GET
	@Path("/update_user")
	@Produces(MediaType.APPLICATION_JSON)
	public String doUpdate(@QueryParam("password") String password, @QueryParam("district") String district, 
									@QueryParam("taluka") String taluka, @QueryParam("username") String username) {
		
		String response = "";
		
		int retCode = updateUser(password, district, taluka, username);
		
		if(retCode == 0) {
			response = Utility.constructJSON("update", true);
		} else if(retCode == 2) {
			response = Utility.constructJSON("update", false, "Special characters are not "
					+ "allowed in Username and Password!");
		} else if(retCode == 3) {
			response = Utility.constructJSON("update", false, "Error occured!");
		}
		
		return response;
	}
	
	private int updateUser(String password, String district, String taluka, String username) {
		int result = 3;
		
		if(Utility.isNotNull(password) && Utility.isNotNull(district) && Utility.isNotNull(taluka)) {
			try {
				if(DbConnection.updateUser(password, district, taluka, username)) {
					result = 0;
				}
			} catch(SQLException sqle) {
				//When special characters are used in password
				if(sqle.getErrorCode() == 1064) {
					result = 2;
				}
			} catch(Exception e) {
				result = 3;
			}
		} else {
			result = 3;
		}
		
		return result;
	}

}
