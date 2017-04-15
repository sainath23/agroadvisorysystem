package com.doitgeek.AgroAdvisorySystem.resource;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.doitgeek.AgroAdvisorySystem.dao.DbConnection;
import com.doitgeek.AgroAdvisorySystem.model.Utility;

@Path("/register")
public class Register {

	@GET
	@Path("/doregister")
	@Produces(MediaType.APPLICATION_JSON)
	/* Query parameters are parameters: http://localhost/<appln-folder-name>
	/register/doregister?name=pqrs&username=abc&password=xyz*/
	public String doRegister(@QueryParam("firstname") String firstname, 
							@QueryParam("lastname") String lastname, 
							@QueryParam("mobileno") long mobileno,
							@QueryParam("username") String username, 
							@QueryParam("password") String password,
							@QueryParam("district") String district, 
							@QueryParam("taluka") String taluka) {
		String response = "";
		
		int retCode = registerUser(firstname, lastname, mobileno, username, 
									password, district, taluka);
		if(retCode == 0) {
			response = Utility.constructJSON("register", true);
		}else if(retCode == 1) {
			response = Utility.constructJSON("register", false, "You are already registered!");
		} else if(retCode == 2) {
			response = Utility.constructJSON("register", false, "Special characters are not "
					+ "allowed in Username and Password!");
		} else if(retCode == 3) {
			response = Utility.constructJSON("register", false, "Error occured!");
		}
		return response;
	}
	
	private int registerUser(String firstname, String lastname, long mobileno, 
								String username, String password, String district, 
								String taluka) {
		int result = 3;
		if(Utility.isNotNull(username) && Utility.isNotNull(password)) {
			try {
				if(DbConnection.insertUser(firstname, lastname, mobileno, username, 
											password, district, taluka)) {
					result = 0;
				}
			} catch(SQLException sqle) {
				//When Primary key violation occurs that means user is already registered
				if(sqle.getErrorCode() == 1062) {
					result = 1;
				}
				//When special characters are used in username or password
				else if(sqle.getErrorCode() == 1064) {
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
