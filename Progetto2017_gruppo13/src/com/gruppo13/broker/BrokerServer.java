package com.gruppo13.broker;

import org.json.simple.parser.ParseException;

public class BrokerServer {

	public static void main(String[] args) throws ParseException {
		
		GestoreBroker g = GestoreBroker.getIstanza();
		System.out.println("server avviato");
		
		//Il server si mette in attesa di richieste da parte dell'utilizzatore e del fornitore
		while(true) { 
			g.riceviMessaggi();
			
		}

	}

}
