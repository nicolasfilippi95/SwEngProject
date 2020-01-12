package com.gruppo13.libreriaJson;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


public abstract class OggettoJson {
	static final String jsonrpc = "2.0";
	String metodo;
	JSONArray parametri;

	abstract JSONObject getJsonObject();
	
	
	void setMetodo(String Metodo) {
		metodo = Metodo;
		
	}
	
	String getMetodo() {
		
		return this.metodo;
	}
	
	void setParametri(JSONArray par) {
		parametri = par;
	}
	
	JSONArray getParametri() {
		
		return this.parametri;
	}
	
	
	String stampaParametri() {
		String s = Arrays.toString(parametri.toArray());
		return s;
	}
	
	String GetJsonRpc() {
		return jsonrpc;
	}
	
}
