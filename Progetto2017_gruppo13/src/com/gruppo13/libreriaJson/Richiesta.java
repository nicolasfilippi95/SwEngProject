package com.gruppo13.libreriaJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.gruppo13.canale.ICanaleClient;

import java.util.UUID;

public class Richiesta extends OggettoJson {
	String id;
	JSONObject messaggio;
	
	public Richiesta() {
		//costruttore per generare id univoco
		UUID idUno = UUID.randomUUID();
		this.id = idUno.toString();
		
		this.messaggio = new JSONObject();
	}
	

	public String getId() {
		return this.id;
	}
	
	public void setRichiesta(String Metodo, JSONArray Par) {

		messaggio.put("jsonrpc", jsonrpc);
		messaggio.put("method", Metodo);
		metodo = Metodo;
		messaggio.put("params", Par);
		parametri = Par;
		messaggio.put("id", id);
		
	}
	
	public JSONObject getJsonObject() {
		return messaggio;
	}
	
	public void inviaJson() {
		messaggio.get("method");
	}
	
	
	public void inviaRichiesta(ICanaleClient c, String ip)
	{
		c.inviaRichiesta(messaggio.toString(), ip);
	}
	

}
