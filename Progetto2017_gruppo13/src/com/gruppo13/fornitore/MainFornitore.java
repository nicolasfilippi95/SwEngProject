package com.gruppo13.fornitore;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gruppo13.broker.IServizio;
import com.gruppo13.broker.ListaServizi;
import com.gruppo13.canale.ICanaleServer;
import com.gruppo13.canale.ZmqServer;
import com.gruppo13.libreriaJson.Errore;
import com.gruppo13.libreriaJson.Errori;
import com.gruppo13.libreriaJson.RisultatoErrato;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainFornitore {
	
	public static ICanaleServer server;
	
	public static void main(String[] args) throws UnknownHostException, ParseException  {

		//create a callable for each method
		   Callable<Boolean> callable1 = new Callable<Boolean>()
		   {
		      @Override
		      public Boolean call() throws Exception
		      {
		         riceviMessaggi();
		         return true;
		      }
		   };

		   Callable<Boolean> callable2 = new Callable<Boolean>()
		   {
		      @Override
		      public Boolean call() throws Exception
		      {
		         return inizializzazione();
		         
		      }
		   };
		   
		   List<Callable<Boolean>> taskList = new ArrayList<Callable<Boolean>>();
		   taskList.add(callable1);
		   taskList.add(callable2);
		   
		   ExecutorService executor = Executors.newFixedThreadPool(2);
		   
		   try
		   {
		      //start the threads and wait for them to finish
		      executor.invokeAll(taskList);
		      
		      //una volta fatta la disconnessione uccido anche il processo server
		      executor.shutdown();

		   }
		   catch (InterruptedException ie)
		   {
		      //do something if you care about interruption;
		   }
		
		
	}	
	
	
	private static void riceviMessaggi() throws ParseException {
		server = new ZmqServer();
		
		boolean sentinella=true;
		while(sentinella) {
			
			String messaggio=server.ricevi();
			
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
			} else {    //restituisco un errore se il metodo non corrisponde a invocazione
				Errore errore = new Errore(Errori.RICHIESTA_INVALIDA);
				RisultatoErrato r = new RisultatoErrato(json, errore);
				server.rispondi(r.getRisposta().toString()); 
			}
			server.rispondi(risultato);
		}
	}
	
	private static Boolean inizializzazione() throws UnknownHostException, ParseException {
		
		//impostiamo il formato della data
				//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.now();
				
				System.out.println(localDate.toString());
				System.out.println("inizio sessione");
				Scanner input = new Scanner(System.in);
				System.out.println();
				System.out.println();
				
				Fornitore f = new Fornitore();
				//String username="pippo"; // assumiamo che il fornitore si sia già registrato con lo username pippo
				String username = new String();
				System.out.println("Inserire l'username:");
				username = input.nextLine();
				f.setUsername(username);
				System.out.println("Benvenuto:"+f.getUsername()+"!");
				boolean disconnessione= false;

				while(disconnessione==false) {
					System.out.println("Premi 1 per visualizzare i tuoi servizi offerti, 2 per chiudere la sessione, 3 per cancellare un servizio, 4 per inserire un servizio ");
					String s = input.nextLine();
					switch(s) 
					{
					case "1": 
					{
						f.visualizzaServiziOfferti(f.getUsername());
						//f.stampaRisposta();
						f.stampaRisultato(f.getRisposta());
						
						break;
					}
					case "2":
					{	
						f.chiudiSessione(f.getUsername());
						disconnessione=true;
						break;
					}
					case "3":
					{
						System.out.println("Inserisci l'id del servizio da cancellare: ");
						String s1 = input.nextLine();				
						f.cancellaServizio(s1);
						//f.stampaRisposta();
						f.stampaRisultato(f.getRisposta());;
						break;
					}
					case "4":
					{
						
						System.out.println("Inserisci il formato input del servizio: ");
						String formatoInput = input.nextLine();
						System.out.println("Inserisci il formato output del servizio: ");
						String formatoOutput = input.nextLine();
						//System.out.println("Inserisci il proprietario del servizio: ");
						//String proprietario = input.nextLine();
						String proprietario=username; // assumiamo che il fornitore si sia già registrato con lo username pippo
						System.out.println("Inserisci il settore del servizio: ");
						String settore = input.nextLine();
						System.out.println("Inserisci la parola chiave del servizio: ");
						String parolaChiave = input.nextLine();
						System.out.println("Inserisci la descrizione del servizio: ");
						String descrizione = input.nextLine();
						System.out.println("Inserisci il titolo del servizio: ");
						String titolo = input.nextLine();
						//System.out.println("Inserisci la data di attivazione del servizio: ");
						String dataAttivazione = localDate.toString();
						String id =null;
						do
						{ 
							System.out.println("Inserisci l'id del servizio: ");
							System.out.println("L'id del servizio da inserire può essere solo somma o moltiplicazione");
							id =input.nextLine();
							if(! id.equals("somma") && !id.equals("moltiplicazione"))
								System.out.println("Errore! ");
								
							
						}  
						while(! id.equals("somma")  && !id.equals("moltiplicazione"));

						
						InetAddress ia = InetAddress.getLocalHost();
						ia.getHostAddress();
						String ip=ia.toString();
						ip="127.0.0.1";
						IServizio servizio= new ServizioSomma(formatoInput, formatoOutput, proprietario,settore, parolaChiave,titolo,descrizione, dataAttivazione, id,ip);				
						f.inserisciServizio(servizio);
						//f.stampaRisposta();
						f.stampaRisultato(f.getRisposta());
						break;
					}
					
					default:
					{
						System.out.println("Errore nell'input");	
					}

					}

					System.out.println("");
				}
				input.close();
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
		
	}
	
}
