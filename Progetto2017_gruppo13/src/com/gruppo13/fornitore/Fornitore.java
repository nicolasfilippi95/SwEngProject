package com.gruppo13.fornitore;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gruppo13.broker.IServizio;
import com.gruppo13.broker.ListaServizi;
import com.gruppo13.canale.ICanaleClient;
import com.gruppo13.canale.ICanaleServer;
import com.gruppo13.canale.ZmqClient;
import com.gruppo13.canale.ZmqServer;
import com.gruppo13.libreriaJson.Batch;
import com.gruppo13.libreriaJson.Notifica;
import com.gruppo13.libreriaJson.Richiesta;


public class Fornitore {
	private String nome;
	private String cognome;
	private String username;
	private String password;
	
	public static ICanaleClient c;
	public static ICanaleServer server;

	public Fornitore() {
		c = new ZmqClient();
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
	
	public void visualizzaServiziOfferti(String username)
	{

		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		temp.add(username);
		//temp.add(idJson);
		r.setRichiesta("Visualizzazione", temp);
		r.inviaRichiesta(c,IpBroker.ip);	
		System.out.println("richiesta di visualizzazione inviata");
	}
	public void chiudiSessione(String username)
	{
		// invia richiesta attraverso la libreria passandogli due stringhe: "Disconnessione" e Username
		
		Notifica n = new Notifica();
		JSONArray temp = new JSONArray();
		temp.add(username);
		n.setNotifica("Disconnessione", temp);
		c = new ZmqClient();
		n.inviaNotifica(c,IpBroker.ip);
		System.out.println("disconnessione inviata");
		
	}
	public void cancellaServizio(String idJson)
	{
		// invia richiesta attraverso la libreria passandogli IdJson del servizio da cancellare
		
		
		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		temp.add(idJson);
		r.setRichiesta("Cancellazione", temp);
		r.inviaRichiesta(c,IpBroker.ip);
		System.out.println("richiesta di cancellazione inviata");
	}
	
	public void stampaRisposta() throws ParseException 
	{
		System.out.println(c.riceviRisposta());
	}
	
	
	public void inserisciServizio(IServizio s2)
	{
		//invia richiesta attraverso la libreria passandogli il servizio
		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		temp.add(s2.getId());
		temp.add(s2.getFI());
		temp.add(s2.getFO());
		temp.add(s2.getProprietario());
		temp.add(s2.getSettore());
		temp.add(s2.getParolaChiave());
		temp.add(s2.getTitolo());
		temp.add(s2.getDescrizione());
		temp.add(s2.getDataAttivazione());
		temp.add(s2.getIp());
		r.setRichiesta("Inserimento", temp);
		r.inviaRichiesta(c,IpBroker.ip);
	}
	
	public void eseguiServizio() throws ParseException
	{
		server= new ZmqServer();
		String messaggio = server.ricevi();
		String risultato = new String();
		JSONParser ogg = new JSONParser();
		JSONObject json = (JSONObject) ogg.parse(messaggio);
		String s=(String)json.get("method");
		if(s.equals("Invocazione")) 
		{
			JSONArray temp=new JSONArray();
			temp=(JSONArray)json.get("params");
			String idJson=temp.get(0).toString();
			String parametri=temp.get(1).toString();
			for(int i=0;i<ListaServizi.getLength();i++)
			{
				if(ListaServizi.getServizio(i).getIdJson().equals(idJson))
				{
					risultato=ListaServizi.getServizio(i).eseguiServizio(parametri);
				}
			}
		}
		server.rispondi(risultato);
				
	}
	
	public void inviaRichiestaBatch(JSONArray array) {
		Batch batch = new Batch(array);
		batch.inviaBatch(c, IpBroker.ip);
	}
	
	public String getRisposta() {
		return c.riceviRisposta();
	}
	
	public void stampaRisultato(String risposta) throws ParseException {
		if(risposta.charAt(0)=='[') {
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
