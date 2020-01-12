package com.gruppo13.libreriaJson;
import org.json.simple.*;


public class RisultatoErrato extends Risultato {
	public RisultatoErrato(JSONObject identificativo, Errore r) {
		super(identificativo);
		this.e = new JSONObject();
		e.put("jsonrpc", jsonrpc);
		e.put("error", r.getError());
		e.put("id", id);
		
		errore = r;
	}
	
	public RisultatoErrato(JSONArray identificativo, Errore r) {
		super(identificativo);
		this.e = new JSONObject();
		e.put("jsonrpc", jsonrpc);
		e.put("error", r.getError());
		e.put("id", id);
		
		errore = r;
	}

	Errore errore;
	JSONObject e;
	
	void setErrore() {
		
	}
	
	Errore getErrore() {
		return this.errore;
	}
	
	void setRisultatoErrato() {
		e.put("jsonrpc", jsonrpc);
		JSONObject temp = errore.getError();
		e.put("error", temp);
		e.put("id", id);
	}
	
	public JSONObject getRisposta() {
		return this.e;
	}
}
