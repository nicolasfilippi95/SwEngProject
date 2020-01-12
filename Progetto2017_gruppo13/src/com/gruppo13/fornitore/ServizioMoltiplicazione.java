package com.gruppo13.fornitore;

import com.gruppo13.broker.IServizio;

public class ServizioMoltiplicazione implements IServizio {
	private String idJson;
	private String formatoInput;
	private String formatoOutput;
	private String proprietario;
	private String settore;
	private String parolaChiave;
	private String titolo;
	private String descrizione;
	private String dataAttivazione;
	private String id;
	private String ip;
	
	public ServizioMoltiplicazione()
	{
		//Random r = new Random();
		//String sIp = 127+"."+r.nextInt(254)+"."+r.nextInt(254)+"."+r.nextInt(253); //253 per evitare l'indirizzo di broadcast
		//ip = sIp;
	}
	public ServizioMoltiplicazione(String pFI, String pFO, String pProprietario, String pSettore, 
			String pParolaChiave, String pTitolo, String pDescrizione, String pData, String pId, String pIp)
	{
		formatoInput=pFI;
		formatoOutput=pFO;
		proprietario=pProprietario;
		settore=pSettore;
		parolaChiave=pParolaChiave;
		titolo=pTitolo;
		descrizione=pDescrizione;
		dataAttivazione=pData;
		id = pId;
		ip=pIp;
	}
	public void setServizioConIp(String pFI, String pFO, String pProprietario, String pSettore, 
						String pParolaChiave, String pTitolo, String pDescrizione, String pData, String pId, String pIp)
	{

		formatoInput=pFI;
		formatoOutput=pFO;
		proprietario=pProprietario;
		settore=pSettore;
		parolaChiave=pParolaChiave;
		titolo=pTitolo;
		descrizione=pDescrizione;
		dataAttivazione=pData;
		id=pId;
		ip=pIp;
		
	}
	
	public void setServizio(String pFI, String pFO, String pProprietario, String pSettore, 
			String pParolaChiave, String pTitolo, String pDescrizione, String pData, String pId)
{

formatoInput=pFI;
formatoOutput=pFO;
proprietario=pProprietario;
settore=pSettore;
parolaChiave=pParolaChiave;
titolo=pTitolo;
descrizione=pDescrizione;
dataAttivazione=pData;
id=pId;

}
	
	public String getIdJson()
	{
		return idJson;
	}
	public void setIdJson(String pIdJson)
	{
		idJson=pIdJson;
	}
	public String getFI()
	{
		return formatoInput;
	}
	public void setFI(String pFormatoInput)
	{
		formatoInput=pFormatoInput;
	}
	public String getFO()
	{
		return formatoOutput;
	}
	public void setFO(String pFormatoOutput)
	{
		formatoOutput=pFormatoOutput;
	}
	public String getProprietario()
	{
		return proprietario;
	}
	public void setProprietario(String pProprietario)
	{
		proprietario=pProprietario;
	}
	public String getSettore()
	{
		return settore;
	}
	public void setSettore(String pSettore)
	{
		settore=pSettore;
	}
	public String getParolaChiave()
	{
		return parolaChiave;
	}
	public void setParolaChiave(String pParolaChiave)
	{
		parolaChiave=pParolaChiave;
	}
	public String getTitolo()
	{
		return titolo;
	}
	public void setTitolo(String pTitolo)
	{
		titolo=pTitolo;
	}
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String pDescrizione)
	{
		descrizione=pDescrizione;
	}
	public String getDataAttivazione()
	{
		return dataAttivazione;
	}
	public void setDataAttivazione(String pDataAttivazione)
	{
		dataAttivazione=pDataAttivazione;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String pId)
	{
		id=pId;
	}
	
	public String eseguiServizio(String parametri)
	{
		int pos= parametri.indexOf(","); //supponiamo che i due numeri specificati dall'utilizzatore come parametri siano separati da una virgola e che non ci siano spazi (es 10,12)
		int num1= Integer.parseInt(parametri.substring(0, pos-1));
		int num2= Integer.parseInt(parametri.substring(pos+1));
		int prodotto= num1*num2;
		String risultato= Integer.toString(prodotto);
		return risultato;
	}
	
	public void setIp(String pIp)
	{
		ip=pIp;
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public void stampaServizio() {
		System.out.print("idJSON: "+ idJson);
		System.out.print(", ");
		System.out.print("Formato Input: "+ formatoInput);
		System.out.print(", ");
		System.out.print("Formato Output: "+ formatoOutput);
		System.out.print(", ");
		System.out.print("Proprietario: "+ proprietario);
		System.out.print(", ");
		System.out.print("Settore: "+ settore);
		System.out.print(", ");
		System.out.print("Parola Chiave: "+ parolaChiave);
		System.out.print(", ");
		System.out.print("Titolo: "+ titolo);
		System.out.print(", ");
		System.out.print("Descrizione: "+ descrizione);
		System.out.print(", ");
		System.out.print("Data Attivazione: "+ dataAttivazione);
		System.out.print(", ");
		System.out.print("id: "+ id);
		System.out.print(", ");
		System.out.print("ip: "+ ip);
		System.out.println();
		
	}
	
}
