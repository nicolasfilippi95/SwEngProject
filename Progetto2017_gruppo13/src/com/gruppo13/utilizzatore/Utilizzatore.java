package com.gruppo13.utilizzatore;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gruppo13.canale.ICanaleClient;
import com.gruppo13.canale.ZmqClient;
import com.gruppo13.libreriaJson.Batch;
import com.gruppo13.libreriaJson.Richiesta;


public class Utilizzatore {
	private String nome;
	private String cognome;
	private String username;
	private String password;
	
	public static ICanaleClient c;
	
	public Utilizzatore() {
		c= new ZmqClient();
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void visualizzaTuttiServizi()
	{
		//invia richiesta attraverso la libreria passandogli la parola chiave
		// attende come risposta un vettore di oggetti
		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		r.setRichiesta("Visualizzazione", temp);
		r.inviaRichiesta(c,IpBroker.ip);	
		System.out.println("richiesta di lista servizi inviata");
		
	}
	
	public void ricercaServizi(String paroleChiavi)
	{
		//invia richiesta attraverso la libreria passandogli la parola chiave
		// attende come risposta un vettore di oggetti
		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		temp.add(paroleChiavi);
		r.setRichiesta("Ricerca", temp);
		r.inviaRichiesta(c,IpBroker.ip);	
		System.out.println("richiesta di ricerca inviata");
		
	}
	public void invocaServizio(String idJson, JSONArray parametri)
	{
		//invia richiesta attraverso la libreria passandogli idJson del servizio da chiamare e i parametri
		//attende come risposta una stringa contente il risultato
		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		temp.add(idJson);
		temp.add(parametri);
		r.setRichiesta("Invocazione", temp);
		r.inviaRichiesta(c,IpBroker.ip);	
		System.out.println("richiesta di invocazione inviata");
	}
	
	public void inviaRichiestaBatch(JSONArray array) {
		Batch batch = new Batch(array);
		batch.inviaBatch(c, IpBroker.ip);
	}
	
	public void stampaRisposta() 
	{
		System.out.println(c.riceviRisposta());
	}
	
	public String getRisposta() {
		return c.riceviRisposta();
	}
	
	public void stampaRisultato(String risposta) throws ParseException {
		if(risposta.charAt(0)=='[') {   //controllo se è arrivato un array
			JSONArray array = new JSONArray();
			JSONParser ogg = new JSONParser();
			array  = (JSONArray) ogg.parse(risposta);
			for(int i=0; i<array.size(); i++) {
				JSONObject obj = new JSONObject();
				JSONParser ogg2 = new JSONParser();
				String el_array = array.get(i).toString();
				obj  = (JSONObject) ogg2.parse(el_array);
				//System.out.println(obj);
				if(obj.containsKey("result")) {
				String risultato = (String) obj.get("result");
				System.out.println(risultato);
				}
				if(obj.containsKey("error")) {
					System.out.println(obj);	
				}
			}
		} else {
			
			JSONObject obj = new JSONObject();
			JSONParser ogg = new JSONParser();
			obj  = (JSONObject) ogg.parse(risposta);
			if(obj.containsKey("result")) {
				String risultato = (String) obj.get("result");
				System.out.println(risultato);
				}
				if(obj.containsKey("error")) {
					System.out.println(obj);	
				}
		}
		
	}
}
