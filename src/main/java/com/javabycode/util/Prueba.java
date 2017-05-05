package com.javabycode.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.javabycode.model.AuthTokenInfo;
import com.javabycode.model.Fruit;

public class Prueba {

	private final static Logger LOG = LoggerFactory.getLogger(Prueba.class);
	
	public static void main(String[] args) {
		
		procesarClienteRestOauth();
	}
	
	/*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders(){
        String plainCredentials="jambrocio:virgo1984";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
         
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    
	public static void procesarClienteRestOauth() {
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
	        
	        //ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.POST, request, String.class);
	        String urlToken = Constantes.URL_TOKEN.replace("{USERNAME}", "admin").replace("{PASSWORD}", "admin123"); 
	        ResponseEntity<AuthTokenInfo> responseToken = restTemplate.exchange(urlToken, HttpMethod.POST, request, AuthTokenInfo.class);
	        
	        AuthTokenInfo tokenInfo = responseToken.getBody();
	        if(responseToken.getStatusCode() == HttpStatus.OK){
	        	System.out.println("========================TOKEN===================================");
	        	System.out.println("access_token [" + tokenInfo.getAccess_token() + "]");
	        	System.out.println("token_type [" + tokenInfo.getToken_type() + "]");
	        	System.out.println("refresh_token [" + tokenInfo.getRefresh_token() + "]");
	        	System.out.println("expires_in [" + String.valueOf(tokenInfo.getExpires_in()) + "]");
	        	System.out.println("scope [" + tokenInfo.getScope() + "]");
	        	
	        	//ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.POST, request, String.class);
		        String urlfRUTAS = Constantes.URL_FRUTAS.replace("{TOKEN}", tokenInfo.getAccess_token()); 
		        ResponseEntity<List<Fruit>> responseFrutas = restTemplate.exchange(urlfRUTAS, HttpMethod.GET, request, new ParameterizedTypeReference<List<Fruit>>() {});
		        
		        List<Fruit> listaFrutas = responseFrutas.getBody();
		        if(responseFrutas.getStatusCode() == HttpStatus.OK){
		        	
		        	System.out.println("========================FRUTAS==================================");
		        	for(Fruit frutas : listaFrutas){
		        		System.out.println("================================================================");
		        		System.out.println("Id : " + frutas.getId());
		        		System.out.println("Name : " + frutas.getName());
		        		System.out.println("Note : " + frutas.getNote());
		        		System.out.println("ProduceBy : " + frutas.getProduceBy());
		        		System.out.println("================================================================");
		        	}
		        	
		        }
		        
	        }
	        	        			
		} catch (Exception e) {
			LOG.error("ERROR:[Exception]", e);
		}
	}
	
}
