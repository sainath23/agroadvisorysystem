package com.doitgeek.AgroAdvisorySystem.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Utility {

	/* Method to check null */
	public static boolean isNotNull(String txt) {
		return txt != null && txt.trim().length() >= 0 ? true:false;
	}
	
	/* Method to construct JSON */
	public static String constructJSON(String tag, boolean status) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", tag);
			obj.put("status", new Boolean(status));
		} catch(JSONException e) {
			
		}
		return obj.toString();
	}
	
	/* Method to construct JSON for SPARQL result */
	public static String constructJSONForSPARQL(String[] result) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("disease", result[0]);
			obj.put("symptoms", result[1]);
			obj.put("control", result[2]);
		} catch(JSONException e) {
			
		}
		return obj.toString();
	}
	
	/* Method to construct JSON to retrieve user details */
	public static String constructJSONForSelectQuery(String[] result) {
		JSONObject obj = new JSONObject();
		try{
			obj.put("firstname", result[0]);
			obj.put("lastname", result[1]);
			obj.put("mobileno", result[2]);
			obj.put("username", result[3]);
			obj.put("password", result[4]);
			obj.put("district", result[5]);
			obj.put("taluka", result[6]);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/* Method to construct JSON with Error Msg */
	public static String constructJSON(String tag, boolean status, String err_msg) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", tag);
			obj.put("status", new Boolean(status));
			obj.put("error_msg", err_msg);
		} catch(JSONException e) {
			
		}
		return obj.toString();
	}
	
}
