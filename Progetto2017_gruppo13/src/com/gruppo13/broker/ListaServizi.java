package com.gruppo13.broker;

import java.util.ArrayList;
import com.gruppo13.broker.IServizio;

public class ListaServizi {
	private static ArrayList<IServizio> listaServizi=new ArrayList<IServizio>();

	public static void aggiungiServizio(IServizio s)
	{
		listaServizi.add(s);
	}
	public static IServizio getServizio(int pos)
	{
		IServizio s =(IServizio) listaServizi.get(pos);
		return s;
	}
	public static int getLength()
	{
		return listaServizi.size();
	}
	public static void cancellaServizio(int pos)
	{
		listaServizi.remove(pos);
	}
	public static void cancellaServizio(String id)
	{
		for(int i=listaServizi.size()-1; i>=0; i++)  //meglio scorrere la lista in modo decrescente per eliminare degli elementi
		{
			if(listaServizi.get(i).getId().equals(id)) {
				listaServizi.remove(i);
			}
		}
	}
	
	public static void cancellaPerDisconnessione (String username) {
		
		for(int i=listaServizi.size()-1; i>=0; i--) {
			if(listaServizi.get(i).getProprietario().equals(username)) {
				listaServizi.remove(i);
			}
		}
	}
	
	public static void visualizzaListaServizi()
	{
		for(int i=0; i<listaServizi.size();i++)
		{
			System.out.println("Id:"+ ListaServizi.getServizio(i).getId()+" Descrizione:"+ListaServizi.getServizio(i).getDescrizione());
		}
	}
}
