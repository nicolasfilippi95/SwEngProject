package com.gruppo13.test;

import static org.junit.Assert.*;
import  org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gruppo13.broker.GestoreBroker;
import com.gruppo13.broker.IServizio;
import com.gruppo13.fornitore.Fornitore;
import com.gruppo13.fornitore.ServizioSomma;

public class TestRegistraServizio {
	IServizio s = new ServizioSomma();
	@Test
	public void test() throws UnknownHostException, ParseException {
		InetAddress ip;
		String sIp=null;
		ip = InetAddress.getLocalHost();
		sIp=ip.getHostAddress();
		s.setServizioConIp("intero", "intero", "Mario", "aritmetica", "somma", "somma", "somma", "21/12/2017", "somma", sIp);
		Fornitore f = new Fornitore();
		f.setUsername("pippo");
		f.inserisciServizio(s);
		GestoreBroker g= GestoreBroker.getIstanza();
		g.riceviMessaggi();
		String r=f.getRisposta();
		JSONObject risultato = new JSONObject();
		JSONParser ogg = new JSONParser();
		risultato = (JSONObject) ogg.parse(r);
		System.out.println(risultato.get("result"));
		assertEquals(risultato.get("result"),"il servizio e' stato inserito");
	}

}
