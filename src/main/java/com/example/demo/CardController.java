package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("card") // http://localhost:8080/card�� ���� 

public class CardController {
	
	@Autowired
	CardMapper cardMapper;

	@RequestMapping(value = "/info", method = RequestMethod.POST) // http://localhost:8080/card (GET)  // client���� card ���� ��� �����ֱ�
	public List<Card> getMessages() {
		System.out.println("###### We got GET message  (/card/info)   ");
		
		List<Card> cardlist = new ArrayList<Card>();
		
		// ��ȿ ī�� ���� ��ȸ API
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
	      factory.setReadTimeout(5000); // �б�ð��ʰ�, ms 
	      factory.setConnectTimeout(3000); // ����ð��ʰ�, ms 
	      HttpClient httpClient = HttpClientBuilder.create() 
	            .setMaxConnTotal(100) // connection pool ���� 
	            .setMaxConnPerRoute(5) // connection pool ���� 
	            .build(); 
	      factory.setHttpClient(httpClient); // ������࿡ ���� HttpClient ���� 
	      RestTemplate restTemplate = new RestTemplate(factory);
	      
		String url= "http://10.3.17.61:8081/v1/mycard/searchavailablecard";
	      String requestBody = (
	            "{"+
	                "\"dataHeader\": {"+
	                "},"+
	                "\"dataBody\":" +
	                     "{\n" +
	                        "   \"nxtQyKey\": \"\""+
	                        "} "+
	              "}");
	   
	      String responseBody = restTemplate.postForObject(url, requestBody, String.class);
	      ObjectMapper objectMapper = new ObjectMapper();
	      try {
	         
	         JSONParser jsonParser = new JSONParser();
	         JSONObject responseBodyJSON = (JSONObject) jsonParser.parse(responseBody);
	         JSONObject dataBody = (JSONObject) responseBodyJSON.get("dataBody");
	         
	         JSONArray cardArray = (JSONArray) dataBody.get("grp001");
	         for(int i=0; i<cardArray.size(); i++) {
	            JSONObject card= (JSONObject) cardArray.get(i);
	            System.out.println("dataHeader- "+i+" CardNo: "+card.get("cardNo"));
	            Card c = new Card();
	            c.setCard_name(card.get("cardname").toString());
	            c.setCard_number(card.get("cardNo").toString());
	            cardlist.add(c);
	         }
	         
	      }
	      catch (ParseException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      
		return cardlist;
	}
	

}
