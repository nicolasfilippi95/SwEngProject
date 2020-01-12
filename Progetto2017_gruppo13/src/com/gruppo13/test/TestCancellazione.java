package com.gruppo13.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gruppo13.broker.IServizio;
import com.gruppo13.broker.ListaServizi;
import com.gruppo13.exceptions.MetodoNonTrovatoException;
import com.gruppo13.fornitore.Fornitore;
import com.gruppo13.fornitore.ServizioMoltiplicazione;
import com.gruppo13.fornitore.ServizioSomma;

public class TestCancellazione {

	@Before
	public  void setUp() throws Exception {
		IServizio s = new ServizioSomma();
		IServizio s2 = new ServizioMoltiplicazione();
		IServizio s3 = new ServizioMoltiplicazione();
		IServizio s4 = new ServizioSomma();
		IServizio s5 = new ServizioSomma();

		s.setServizio("intero", "intero", "Mario", "aritmetica", "somma", "somma", "somma", "21-12-2017", "somma");
		s2.setServizio("intero", "intero", "pippo", "algebra", "somma", "somma", "algebra", "21-12-2017", "algebraId");
		s3.setServizio("intero", "intero", "Mario", "geografia", "Geo", "mondo", "geografia", "21-10-2017", "geografiaId");
		s4.setServizio("intero", "intero", "pippo", "storia", "somma", "somma", "storia", "25-01-2017", "storiaId");
		s5.setServizio("intero", "intero", "Andrea", "aritmetica", "somma", "somma", "somma", "13-12-2017", "somma");



		ListaServizi.visualizzaListaServizi();

		Fornitore f = new Fornitore();
		f.inserisciServizio(s);
		f.inserisciServizio(s2);
		f.inserisciServizio(s3);
		f.inserisciServizio(s4);
		f.inserisciServizio(s5);

	}

	@After
	public  void tearDown() throws Exception {
		System.out.println("------------ end --------------");
	}
	
	
	@Test
	public void testVisualizzazioneServiziOfferti() throws MetodoNonTrovatoException {
		Fornitore f = new Fornitore();
		f.cancellaServizio("algebraId");
	}
	
	@Test(expected = MetodoNonTrovatoException.class)
	public void testRicerca() throws MetodoNonTrovatoException {
		Fornitore f = new Fornitore();
		f.cancellaServizio("disegnoId");
	}

}
