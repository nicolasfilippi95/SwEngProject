package com.gruppo13.libreriaJson;
import org.json.simple.JSONObject;

public class RisultatoCorretto extends Risultato {
	String risultato;
	JSONObject r;
	
	public RisultatoCorretto(JSONObject identificativo, String risultato) {
		super(identificativo);
		this.r = new JSONObject();
		this.risultato = risultato;
		
		//perch� abbiamo fatto cos�? unire con setRisultatoCorretto
	}

	public String getRisultato() {
		return this.risultato;
	}
	
	public void stampaRisultato() {
		
		System.out.println("il risultato ottenuto �: " + risultato);
	}
	
	public void setRisultatoCorretto() {
		r.put("jsonrpc", jsonrpc);
		r.put("result", risultato);
		r.put("id", id);
	}
	
	public JSONObject getRisposta() {
		return this.r;
	}
	
}
