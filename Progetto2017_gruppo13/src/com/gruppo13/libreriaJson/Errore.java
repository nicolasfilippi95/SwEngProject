package com.gruppo13.libreriaJson;
import org.json.simple.*;

public class Errore {
	private int codice;
	private String messaggio;
	private JSONObject error;
	
	public Errore(Errori errore) {
		this.error = new JSONObject();
		
		switch(errore) {
		case ERRORE_PARSE:
			codice = -32700;
			messaggio = "metodo non trovato";
			error.put("code", codice);
			error.put("message", messaggio);
			break;
			
		case RICHIESTA_INVALIDA:
			codice = -32600;
			messaggio = "richiesta non valida";
			error.put("code", codice);
			error.put("message", messaggio);
			break;
		
		case METODO_NON_TROVATO: 
			codice = -32601;
			messaggio = "metodo non trovato";
			error.put("code", codice);
			error.put("message", messaggio);
			break;
			
		case PARAMETRI_INVALIDI:
			codice = -32602;
			messaggio = "parametri non validi";
			error.put("code", codice);
			error.put("message", messaggio);
			break;
			
		case ERRORE_INTERNO:
			codice = -32603;
			messaggio = "errore interno";
			error.put("code", codice);
			error.put("message", messaggio);
			break;
			
		default:
			codice = -32000;
			messaggio = "errore sconosciuto";
			error.put("code", codice);
			error.put("message", messaggio);
			
	}
	}
	
	
	public int getCodice() {
		return this.codice;
	}
	
	
	public String getMessaggio() {
		return this.messaggio;
	}
	
	public JSONObject getError() {
		return this.error;
	}
	
	
	
	public void stampaErrore() {
		
		System.out.println("è stato generato il seguente errore: " + codice);
		System.out.println(messaggio);
	
		
	}
}
