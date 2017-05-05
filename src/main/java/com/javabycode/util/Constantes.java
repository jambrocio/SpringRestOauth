package com.javabycode.util;

public class Constantes {

	public static String URL = "http://localhost:8080/SpringRestOauth/";
	public static String URL_TOKEN = URL + "oauth/token?grant_type=password&username={USERNAME}&password={PASSWORD}";
	public static String URL_FRUTAS = URL + "fruits?access_token={TOKEN}";
	
}
