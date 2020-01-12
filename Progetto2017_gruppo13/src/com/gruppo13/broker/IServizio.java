package com.gruppo13.broker;

import java.util.Random;

public interface IServizio {
		
	public void setServizioConIp(String pFI, String pFO, String pProprietario, String pSettore, 
						String pParolaChiave, String pTitolo, String pDescrizione, String pData, String pId, String pIp);
	
	
	public void setServizio(String pFI, String pFO, String pProprietario, String pSettore, 
			String pParolaChiave, String pTitolo, String pDescrizione, String pData, String pId);

	
	public String getIdJson();
	
	public void setIdJson(String pIdJson);
	
	public String getFI();
	
	public void setFI(String pFormatoInput);
	
	public String getFO();
	
	public void setFO(String pFormatoOutput);
	
	public String getProprietario();
	
	public void setProprietario(String pProprietario);
	
	public String getSettore();
	
	public void setSettore(String pSettore);
	
	public String getParolaChiave();
	
	public void setParolaChiave(String pParolaChiave);
	
	public String getTitolo();
	
	public void setTitolo(String pTitolo);
	
	public String getDescrizione();
	
	public void setDescrizione(String pDescrizione);
	
	public String getDataAttivazione();
	
	public void setDataAttivazione(String pDataAttivazione);
	
	public String getId();
	
	public void setId(String pId);
	
	
	public String eseguiServizio(String parametri);
	
	
	public void setIp(String pIp);
	
	
	public String getIp();
	
	
	public void stampaServizio();

	
}
