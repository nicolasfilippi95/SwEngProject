package com.gruppo13.libreriaJson;
import org.json.simple.*;

public abstract class Risultato {
	static final String jsonrpc = "2.0";
	protected String id;
	
	public Risultato (JSONObject o) {
		//prende id dell'oggetto a cui deve rispondere
			this.id = (String)(o.get("id"));

	}
	
	public Risultato (JSONArray a) {
		//prende id dell'oggetto a cui deve rispondere
			//this.id = (String)(a.get("id"));

	}
	
	String getId() {
		return this.id;
	}
	
	abstract JSONObject getRisposta();

	
}
