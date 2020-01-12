package com.gruppo13.canale;
import org.zeromq.*;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.gruppo13.utilizzatore.IpBroker;

public class ZmqClient implements ICanaleClient{
	private Context ctx;
	private Socket socket;
	private int port;
	private String ip;
	public ZmqClient ()
	{
		ctx = ZMQ.context(1);
		port=5555;
		ip=IpBroker.ip;
	}
	public void inviaRichiesta (String pRichiesta, String pIp)
	{	
		ip=pIp;
		socket=ctx.socket(ZMQ.DEALER);
		socket.connect("tcp://"+ip+":" + String.valueOf(port));
		
		socket.send(pRichiesta.getBytes());
		
		System.out.println("Inviato: "+ pRichiesta);
		
	}
	public String riceviRisposta()
	{
		String risposta=socket.recvStr();
		socket.close();
		//System.out.println("Ricevuto: "+ risposta);
		return risposta;
	}
	public void inviaNotifica (String pNotifica, String pIp)
	{	
		ip=pIp;
		socket=ctx.socket(ZMQ.DEALER);
		socket.connect("tcp://"+ip+":" + String.valueOf(port));		
		socket.send(pNotifica.getBytes());		
		System.out.println("Inviato: "+ pNotifica);		
		socket.close();
		
	}
	public void send (String messaggio)
	{
		socket = ctx.socket(ZMQ.DEALER);
		socket.connect("tcp://" + ip + ":" + String.valueOf(port));
		socket.send(messaggio.getBytes());
		socket.close();
	}
	
	public void setIp (String ip) {
		this.ip = ip;
	}
	
	
}
