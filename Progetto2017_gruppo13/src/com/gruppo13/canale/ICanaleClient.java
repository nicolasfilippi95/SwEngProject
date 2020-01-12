package com.gruppo13.canale;

public interface ICanaleClient 
{
	/*public static String inviaRichiesta (String pRichiesta)
	{
		ZmqClient c = new ZmqClient();
		return c.inviaRichiesta(pRichiesta);
		
	}
	*/
	public void inviaRichiesta (String pRichiesta, String pIp);
	public String riceviRisposta();
	public void inviaNotifica (String pNotifica, String pIp);
	public void send (String messaggio);
	
}
