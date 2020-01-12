package com.gruppo13.broker;

import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gruppo13.canale.ICanaleClient;
import com.gruppo13.canale.ICanaleServer;
import com.gruppo13.canale.ZmqServer;
import com.gruppo13.exceptions.*;
import com.gruppo13.fornitore.ServizioSomma;
import com.gruppo13.libreriaJson.Errore;
import com.gruppo13.libreriaJson.Errori;
import com.gruppo13.libreriaJson.Richiesta;
import com.gruppo13.libreriaJson.RisultatoCorretto;
import com.gruppo13.libreriaJson.RisultatoErrato;

public class GestoreBroker {

	public static GestoreBroker istanza;
	public static ICanaleServer s;
	public static ICanaleClient c;
	public GestoreBroker() {


	}

	public static GestoreBroker getIstanza() {
		if(istanza == null) {			
			istanza = new GestoreBroker();
			s= new ZmqServer();
		}
		return istanza;
	}

	public void riceviMessaggi() throws ParseException 
	{

		boolean sentinella=true; 
		while(sentinella==true) 
		{

			String messaggio=s.ricevi();

			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			int contatore=0;
			
			if(messaggio.charAt(0)=='[') {  //controllo se è arrivato un singolo messaggio o un array
				System.out.println("array trovato");
				JSONParser ogg = new JSONParser();
				try {
					array = (JSONArray) ogg.parse(messaggio);
					if(array.size()==0) {
						Errore errore = new Errore(Errori.RICHIESTA_INVALIDA);
						RisultatoErrato r = new RisultatoErrato(array, errore);
						s.rispondi(r.getRisposta().toString());
						sentinella = false;
					}
					contatore = array.size();
				} catch(ParseException e) {
					Errore errore = new Errore(Errori.ERRORE_PARSE);
					RisultatoErrato r = new RisultatoErrato(array, errore);
					s.rispondi(r.getRisposta().toString()); 
					sentinella = false;
				}
			} else {

				JSONParser ogg = new JSONParser();
				json = (JSONObject) ogg.parse(messaggio);
				array.add(json);
				contatore = 1;
			}

			JSONArray risultatoArray = new JSONArray();  //array da restituire
			
			for(int j=0; j< contatore; j++) {
				json = (JSONObject) array.get(j);
				
				
				//In base alla richiesta ricevuta dal client, effettua la relativa operazione
				switch ((String)json.get("method"))
				{
				case "Disconnessione":
					JSONArray temp3=new JSONArray();
					temp3=(JSONArray)json.get("params");
					cancellaPerDisconnessioneFornitore(temp3.get(0).toString());
					
					if(contatore==1) {
						sentinella=false;
						break;
					} else {
						if(j==contatore-1) {
							s.rispondi(risultatoArray.toString());
							sentinella=false;
							break;
						}
						break;
					}
					

				case "Cancellazione" :

					try {
						
						JSONArray temp4=new JSONArray();
						temp4=(JSONArray)json.get("params");
						cancellaVolontario(temp4.get(0).toString());
					} catch (MetodoNonTrovatoException e2) {
						Errore errore = new Errore(Errori.METODO_NON_TROVATO);
						RisultatoErrato r = new RisultatoErrato(json, errore);
						if(contatore==1) {   //se abbiamo una sola richiesta
							s.rispondi(r.getRisposta().toString());
							break;
						} else {
							if(j==contatore-1) { //caso ultimo(ultima richiesta dell'array), rispondo restituendo l'array
								risultatoArray.add(r.getRisposta());
								s.rispondi(risultatoArray.toString());
								break;
							} else {		
								risultatoArray.add(r.getRisposta());  //aggiungo all'array la risposta
								break;
							}
						}					
					}
					RisultatoCorretto risultato = new RisultatoCorretto(json, "il servizio e' stato cancellato");
					risultato.setRisultatoCorretto();
					
					if(contatore==1) {
						s.rispondi(risultato.getRisposta().toString());
						sentinella=false;
						break;
					} else {
						risultatoArray.add(risultato.getRisposta());
						if(j==contatore-1) {	
							s.rispondi(risultatoArray.toString());
							sentinella=false;
							break;
						}
						System.out.println("elemento aggiunto all'array");
						break;
					}
					

				case "Inserimento" :
					JSONArray temp=new JSONArray();
					temp=(JSONArray)json.get("params");
					// Crea l'oggetto servizio e inserisce tutti i suoi attributi
					IServizio s2=new ServizioSomma();
					s2.setId(temp.get(0).toString());
					s2.setFI(temp.get(1).toString());
					s2.setFO(temp.get(2).toString());
					s2.setProprietario(temp.get(3).toString());
					s2.setSettore(temp.get(4).toString());
					s2.setParolaChiave(temp.get(5).toString());
					s2.setTitolo(temp.get(6).toString());
					s2.setDescrizione(temp.get(7).toString());
					s2.setDataAttivazione(temp.get(8).toString());
					s2.setIp(temp.get(9).toString());
					registraServizio(s2);		
					
					RisultatoCorretto risultato2 = new RisultatoCorretto(json, "il servizio e' stato inserito");
					risultato2.setRisultatoCorretto();
					if(contatore==1) {
						s.rispondi(risultato2.getRisposta().toString());
						sentinella=false;
						break;
					} else {
						risultatoArray.add(risultato2.getRisposta());
						
						if(j==contatore-1) {
							s.rispondi(risultatoArray.toString());
							sentinella=false;
							break;
						}
						break;
					}

				case "Visualizzazione" :
					//GestoreVisualizzaServizi g4 = GestoreVisualizzaServizi.getIstanza();
					JSONArray temp2=new JSONArray();
					temp2=(JSONArray)json.get("params");
					//System.out.println("entrato in visualizzazione");
					if(temp2.isEmpty()) {
						ArrayList<IServizio> l=visualizzaTuttiServizi();
						String lista = new String();
						for(int i=0;i<l.size();i++)
						{
							lista=lista+"Nome Servizio: "+l.get(i).getId()+", Descrizione: "+l.get(i).getDescrizione()+", Id Json: "+l.get(i).getIdJson()+", Proprietario: "+l.get(i).getProprietario()+"\n";
						}
						
						if(l.isEmpty()) {
							lista = "Non sono stati trovati servizi";
						}
						
						RisultatoCorretto risultato3 = new RisultatoCorretto(json, lista);
						risultato3.setRisultatoCorretto();
						if(contatore==1) {
							s.rispondi(risultato3.getRisposta().toString());
							//s.rispondi(lista);
							sentinella=false;
							break;
						} else {
							risultatoArray.add(risultato3.getRisposta());
							if(j==contatore-1) {
								s.rispondi(risultatoArray.toString());
								//s.rispondi(lista);
								sentinella=false;
								break;
							}
							System.out.println("elemento aggiunto all'array");
							break;
						}
					} else {


						 {
							ArrayList<IServizio> l;
							l = visualizzaServiziOfferti(temp2.get(0).toString());
							String lista = new String();
							
							for(int i=0;i<l.size();i++)
							{
								lista=lista+"Nome Servizio: "+l.get(i).getId()+", Descrizione: "+l.get(i).getDescrizione()+", Id Json: "+l.get(i).getIdJson()+", Proprietario: "+l.get(i).getProprietario()+"\n";
							}
							
							if(l.isEmpty()) {
								lista = "Non sono stati trovati servizi";
							}
							
							RisultatoCorretto risultato4 = new RisultatoCorretto(json, lista);
							risultato4.setRisultatoCorretto();

							if(contatore==1) {
								s.rispondi(risultato4.getRisposta().toString());
								//s.rispondi(lista);
								sentinella=false;
								break;
							} else {
								risultatoArray.add(risultato4.getRisposta());
								if(j==contatore-1) {
									s.rispondi(risultatoArray.toString());
									//s.rispondi(lista);
									sentinella=false;
									break;
								}
								//System.out.println("elemento aggiunto all'array");
								break;

							}


						}

					}


				case "Ricerca":
					//GestoreRicercaServizio g5 = GestoreRicercaServizio.getIstanza();
					JSONArray temp5=new JSONArray();
					temp5=(JSONArray)json.get("params");
					ArrayList<IServizio> lRicerca;
						lRicerca = ricerca(temp5.get(0).toString());
						String lista2 = new String();
						for(int i=0;i<lRicerca.size();i++)
						{
							lista2=lista2+"Id Json: "+lRicerca.get(i).getIdJson()+", Descrizione: "+lRicerca.get(i).getDescrizione()+", Proprietario: "+lRicerca.get(i).getProprietario()+"\n";
						}
						
						if(lista2.isEmpty()) {
							lista2 = "Non sono stati trovati servizi con questa parola chiave";
						}
						RisultatoCorretto risultato5 = new RisultatoCorretto(json, lista2);
						risultato5.setRisultatoCorretto();
						
						if(contatore==1) {
							s.rispondi(risultato5.getRisposta().toString());
							//s.rispondi(lista2);
							sentinella=false;
							break;
						} else {
							risultatoArray.add(risultato5.getRisposta());
							if(j==contatore-1) {
								s.rispondi(risultatoArray.toString());
								//s.rispondi(lista2);
								sentinella=false;
								break;
							}
							break;
						}
						


				case "Invocazione":
					//GestoreInvocaServizio g6 = GestoreInvocaServizio.getIstanza();
					JSONArray temp6=new JSONArray();
					temp6=(JSONArray)json.get("params");
					String idJson=temp6.get(0).toString();
					JSONArray parametri = new JSONArray();
					System.out.println(temp6);
					System.out.println(idJson);
					parametri = (JSONArray) temp6.get(1);

					System.out.println(parametri);
					try {
						invocaServizio(idJson, parametri);
						sentinella=false;
						break;
					} catch (MetodoNonTrovatoException e) {
						Errore errore = new Errore(Errori.METODO_NON_TROVATO);
						//JSONObject err = errore.getError();
						RisultatoErrato r = new RisultatoErrato(json, errore);
						
						if(contatore==1) {
							s.rispondi(r.getRisposta().toString());
							break;
						} else {
							risultatoArray.add(r.getRisposta());
							if(j==contatore-1) {
								s.rispondi(risultatoArray.toString());
								break;
							}
							break;
						}
					}

				}
				//System.out.println("finito "+(j+1)+" cicli for");
			}
		}
	}

	
	//porzione cancellazione
	private void cancellaVolontario(String pIdJson) throws MetodoNonTrovatoException
	{
		boolean temp = false;
		for (int i=0;i<ListaServizi.getLength();i++)
		{
			if(ListaServizi.getServizio(i).getIdJson().equals(pIdJson))
			{
				temp = true;
				ListaServizi.cancellaServizio(i);
				break;
			}
			
		}
		if(!temp) {
			throw new MetodoNonTrovatoException();
		}
	}
	
	
	private void cancellaPerDisconnessioneFornitore(String username)
	{
		
		if(ListaServizi.getLength()==0) {
			
		} else {
		
			ListaServizi.cancellaPerDisconnessione(username);
		}
	}
	
	//porzione invocazione
	private void invocaServizio(String idJson, JSONArray parametri) throws MetodoNonTrovatoException {
		String ipFornitore=null;
		Richiesta r = new Richiesta();
		JSONArray temp = new JSONArray();
		temp.add(idJson);
		temp.add(parametri);

		r.setRichiesta("Invocazione", temp);
		boolean trovato = false;
		for(int i=0;i<ListaServizi.getLength();i++)
		{
			if(ListaServizi.getServizio(i).getIdJson().equals(idJson))
			{
				trovato = true;
				ipFornitore=ListaServizi.getServizio(i).getIp();
			}
		}
		if(trovato) {
			r.inviaRichiesta(c,ipFornitore);	
			System.out.println("richiesta di invocazione inviata");
		} else throw  new MetodoNonTrovatoException();
		
		
	}
	
	
	//porzione registra servizio
	private void registraServizio(IServizio s)
	{
		boolean IdJsonUguale=false;
		boolean sentinella=false;
		int cont=0;
		String idJson;
		for(int i=0; i<ListaServizi.getLength(); i++) {
			
			
			if(s.getId().equals(ListaServizi.getServizio(i).getIdJson()))
			{
				IdJsonUguale=true;
			}
		}
		if (IdJsonUguale==false)
		{
			s.setIdJson(s.getId());
			ListaServizi.aggiungiServizio(s);
		}
		else 
		{
			while(sentinella==false)
			{
				cont++;
				idJson= s.getId()+cont;
				IdJsonUguale=false;
				for(int k=0; k<ListaServizi.getLength(); k++)
				{
					if(idJson.equals(ListaServizi.getServizio(k).getIdJson()))
					{
						
						IdJsonUguale=true;
						
					}
				}
				if (IdJsonUguale==false)
				{
					s.setIdJson(idJson);
					ListaServizi.aggiungiServizio(s);
					sentinella=true;
				}

				
			}
		}
	}
	
	
	//porzione Ricerca servizio
	private void ricerca()
	{
		for(int i=0; i<ListaServizi.getLength();i++)
		{
			System.out.println("Id Json: "+ ListaServizi.getServizio(i).getId()+" Descrizione: "+ListaServizi.getServizio(i).getDescrizione());
		}
	}
	public ArrayList<IServizio> ricerca(String paroleChiave)
	{

		ArrayList<Integer> posRisultati = new ArrayList<Integer>();
		ArrayList<IServizio> l = new ArrayList<IServizio>();
		//controllo se le parole chiavi sono presenti tra tutti i metadati
		for(int i=0; i<ListaServizi.getLength();i++)
		{
			if(ListaServizi.getServizio(i).getId().contains(paroleChiave))
				posRisultati.add(i);
			else if (ListaServizi.getServizio(i).getIdJson().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getProprietario().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getSettore().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getParolaChiave().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getTitolo().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getDescrizione().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getDataAttivazione().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getFI().contains(paroleChiave))
				posRisultati.add(i);
			else if(ListaServizi.getServizio(i).getFO().contains(paroleChiave))
				posRisultati.add(i);
		}
		//visualizzo Id Json e descrizione dei servizi in cui compaiono le parole chiavi nei metadati

			for(int k=0; k<posRisultati.size(); k++)
			{
				
				l.add(ListaServizi.getServizio(posRisultati.get(k)));
			}
			return l;

	}
	
	//porzione visualizza servizi
	private ArrayList<IServizio> visualizzaTuttiServizi()
	{
		ArrayList<IServizio> l = new ArrayList<IServizio>();
		for(int i=0; i<ListaServizi.getLength();i++)
		{
				l.add(ListaServizi.getServizio(i));
		
		}
		return l;
	}
	
	private ArrayList<IServizio> visualizzaServiziOfferti(String username)
	{
		ArrayList<IServizio> l = new ArrayList<IServizio>();
		for(int i=0; i<ListaServizi.getLength();i++)
		{
			if(ListaServizi.getServizio(i).getProprietario().equals(username))
			{
				l.add(ListaServizi.getServizio(i));
			}
		}
			return l;	
	}
	
}