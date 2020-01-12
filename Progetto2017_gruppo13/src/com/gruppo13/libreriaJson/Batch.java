package com.gruppo13.libreriaJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.gruppo13.canale.ICanaleClient;

public class Batch {
	JSONArray array;
	
	public Batch(JSONArray array) {
		this.array = array;
	};
	
	public void setArray(JSONArray array) {
	this.array = array;
	
	}
	
	public JSONArray getArray() {
		return this.array;
	}
	
	public void inviaBatch(ICanaleClient c, String ip) {
		
		//leggere tutto l'array, se esiste 
		//almeno una richiesta inviare richiesta, altrimenti notifica
		boolean flag=true; 
		for(int i=0; i<array.size(); i++) {
			JSONObject temp = (JSONObject) array.get(i);
			if(temp.containsKey("id")) {
				flag=true;
				break;
			} else flag = false;
		}
		if(flag==true) {
			c.inviaRichiesta(array.toString(), ip);
		}
		if(flag==false) {
			c.inviaNotifica(array.toString(), ip);
		}
	}
}
