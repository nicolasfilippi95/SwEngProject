package com.gruppo13.canale;

import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class ZmqServer implements ICanaleServer{
	ZFrame identity;
	ZFrame empty;
	private Context ctx;
	private Socket socket;
	private int port;
//	String text;
	public ZmqServer()
	{
		
		identity=null;
		empty=null;
		ctx = ZMQ.context(1);
		socket = ctx.socket(ZMQ.ROUTER);
		port=5555;
		socket.bind("tcp://*:"+String.valueOf(port));
		
	}
	public String ricevi()
	{
		ZMsg messaggio = ZMsg.recvMsg(socket);
		System.out.println("Messaggio ricevuto");
		identity = messaggio.pop();
		//il dealer non usa il frame vuoto
		if (messaggio.size()==2) 
		{
			empty = messaggio.pop();
		} 
		else 
		{
			empty = null;
		}
		return messaggio.pop().toString();
	}
	
	
	
	public void rispondi(String s) throws UnsupportedOperationException
	{
		
		if(identity==null) 
		{
			throw new UnsupportedOperationException("Receiver undefined"); 
		}
		
		
		ZMsg messaggio = new ZMsg();
		messaggio.push(new ZFrame (s.getBytes()));
		if(empty!=null)
		{
			messaggio.push(empty);
		}
		messaggio.push(identity);
		messaggio.send(socket);
		
		identity=null;
		empty=null;

		
	}
	
	}

	
	
