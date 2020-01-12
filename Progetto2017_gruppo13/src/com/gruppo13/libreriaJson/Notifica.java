package com.gruppo13.libreriaJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.gruppo13.canale.ICanaleClient;


public class Notifica extends OggettoJson {
//	A Notification is a Request object without an "id" member. 
//	A Request object that is a Notification signifies the Client's lack of interest 
//	in the corresponding Response object, and as such no Response object
//	needs to be returned to the client. 
//	The Server MUST NOT reply to a Notification, 
//	including those that are within a batch request.
	JSONObject notifica;
	
	public Notifica() {
		this.notifica = new JSONObject();
	}
	
	
	public void setNotifica (String Metodo, JSONArray par) {
		
		notifica.put("jsonrpc", jsonrpc);
		notifica.put("method", Metodo);
		metodo = Metodo;
		notifica.put("params", par);
		parametri = par;

	}
	
	JSONObject getJsonObject () {
		return this.notifica;
	}
	
	public void inviaNotifica(ICanaleClient c, String ip)
	{
		 c.inviaNotifica(notifica.toString(), ip);
	}
	

}
