package com.gruppo13.test;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Test;

import com.gruppo13.broker.GestoreBroker;
import com.gruppo13.broker.IServizio;
import com.gruppo13.broker.ListaServizi;
import com.gruppo13.exceptions.MetodoNonTrovatoException;
import com.gruppo13.fornitore.Fornitore;
import com.gruppo13.fornitore.ServizioMoltiplicazione;
import com.gruppo13.fornitore.ServizioSomma;
import com.gruppo13.utilizzatore.Utilizzatore;

public class TestEsecuzione {

	public  void setUp() throws Exception {
		IServizio s = new ServizioSomma();
		IServizio s2 = new ServizioMoltiplicazione();

		s.setServizio("intero", "intero", "Mario", "aritmetica", "somma", "somma", "somma", "21-12-2017", "somma");
		s2.setServizio("intero", "intero", "pippo", "algebra", "somma", "somma", "algebra", "21-12-2017", "algebraId");



		ListaServizi.visualizzaListaServizi();

		Fornitore f = new Fornitore();
		f.inserisciServizio(s);
		f.inserisciServizio(s2);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("------------ end --------------");
	}
	
	
	@Test
	public void testEsecuzione() throws MetodoNonTrovatoException, ParseException {
		Utilizzatore u = new Utilizzatore();
		GestoreBroker g= GestoreBroker.getIstanza();
		g.riceviMessaggi();
		JSONArray array = new JSONArray();
		array.add(12);
		array.add(13);
		u.invocaServizio("somma", array);
		u.stampaRisposta();
	}
	
	@Test(expected = MetodoNonTrovatoException.class)
	public void testRicerca() throws MetodoNonTrovatoException {
		Utilizzatore u = new Utilizzatore();
		JSONArray array = new JSONArray();
		array.add(12);
		array.add(13);
		u.invocaServizio("divisione", array);
	}

}
