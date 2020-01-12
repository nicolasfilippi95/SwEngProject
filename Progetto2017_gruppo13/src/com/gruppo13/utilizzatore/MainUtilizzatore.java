package com.gruppo13.utilizzatore;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gruppo13.libreriaJson.Richiesta;

public class MainUtilizzatore {

	public static void main(String[] args) throws UnknownHostException, ParseException  {

		//impostiamo il formato della data
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		
		System.out.println(localDate.toString());
		System.out.println("inizio sessione");
		
		System.out.println();
		System.out.println();
		
		//costruiamo un utente utilizzatore
		Utilizzatore u = new Utilizzatore();
		

		while(true) {
			System.out.println("Premi 1 per visualizzare tutti i servizi, 2 per cercare i servizi, 3 per invocare un servizio, 4 per costruire una richiesta batch ");
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();

			switch(s) 
			{
			case "1": 
			{
				u.visualizzaTuttiServizi();
				//u.stampaRisposta();
				u.stampaRisultato(u.getRisposta());
				break;
			}
			case "2":
			{
				System.out.println("Inserisci le parole chiavi da cercare: ");
				String s2 = input.nextLine();	
				u.ricercaServizi(s2);
				//u.stampaRisposta();
				u.stampaRisultato(u.getRisposta());
				break;
			}
			case "3":
			{
				System.out.println("Inserisci l'id del servizio da invocare: ");
				String s3 = input.nextLine();	
				System.out.println("Inserisci i parametri del servizio (separati da una virgola): ");
				String s4 = input.nextLine();				
				s4 = "["+s4+"]";
				JSONParser ogg = new JSONParser();
				JSONArray Jarray = new JSONArray();
				Jarray = (JSONArray) ogg.parse(s4);
				u.invocaServizio(s3, Jarray);
				//u.stampaRisposta();
				u.stampaRisultato(u.getRisposta());
				break;
			}
			
			case "4": {
				String flag;
				JSONArray batch = new JSONArray();
				do {
					System.out.println("inserisci il numero di una delle altre richieste, altrimenti premi x per inviare");
					flag = input.nextLine();
					if(flag.equals("1")) {
						String metodo1 = "Visualizzazione";
						JSONArray array1 = new JSONArray();
						Richiesta richiesta1 = new Richiesta();
						richiesta1.setRichiesta(metodo1, array1);
						batch.add(richiesta1.getJsonObject());
					}
					
					if(flag.equals("2")) {
						System.out.println("Inserisci le parole chiavi da cercare: ");
						String s2 = input.nextLine();
						
						String metodo2 = "Ricerca";
						JSONArray array2 = new JSONArray();
						array2.add(s2);
						Richiesta richiesta2 = new Richiesta();
						richiesta2.setRichiesta(metodo2, array2);
						batch.add(richiesta2.getJsonObject());
					}
					
					if(flag.equals("3")) {
						System.out.println("Inserisci l'id del servizio da invocare: ");
						String s3 = input.nextLine();	
						System.out.println("Inserisci i parametri del servizio: ");
						String s4 = input.nextLine();
						
						String metodo3 = "Invocazione";
						JSONArray array3 = new JSONArray();
						array3.add(s3);
						array3.add(s4);
						Richiesta richiesta3 = new Richiesta();
						richiesta3.setRichiesta(metodo3, array3);
						batch.add(richiesta3.getJsonObject());

					}
					
					
				} while (!flag.equals("x"));
				u.inviaRichiestaBatch(batch);
				//u.stampaRisposta();
				u.stampaRisultato(u.getRisposta());
			}
			case "x" : {
				break;
			}
			
			default:
			{
				System.out.println("Errore nell'input");	
			}

			}
			System.out.println("");
		}
		

	}	
}
