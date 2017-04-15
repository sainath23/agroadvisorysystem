package com.doitgeek.AgroAdvisorySystem.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.doitgeek.AgroAdvisorySystem.dao.DbConnection;
import com.doitgeek.AgroAdvisorySystem.model.Utility;

@Path("/login")
public class Login {

	@GET
	@Path("/dologin")
	@Produces(MediaType.APPLICATION_JSON)
	/* Query parameters are parameters: http://localhost/<appln-folder-name>/login
		/dologin?username=abc&password=xyz */
	public String doLogin(@QueryParam("username") String username, 
							@QueryParam("password") String password) {
		String response = "";
		if(checkCredentials(username, password)) {
			response = Utility.constructJSON("login", true);
		}else {
			response = Utility.constructJSON("login", false, "Incorrect Username or Password!");
		}
		return response;
	}
	
	/* Method to check whether the entered credentials are valid */
	private boolean checkCredentials(String username, String password) {
		boolean result = false;
		if(Utility.isNotNull(username) && Utility.isNotNull(password)){
            try {
                result = DbConnection.checkLogin(username, password);
            } catch (Exception e) {
                result = false;
            }
        }else{
            result = false;
        }
        return result;
	}
}
