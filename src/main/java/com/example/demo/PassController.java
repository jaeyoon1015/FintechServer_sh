package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("pass") // http://localhost:8080/pass�� ���� 

public class PassController {

	@Autowired
	PassMapper passMapper;
	
	@RequestMapping(method = RequestMethod.GET) // http://localhost:8080/user (GET)
	public String getMessages() {
		System.out.println("################  We got GET message");
		return "hello";
	}
	
	@RequestMapping(value="/give", method = RequestMethod.POST) // http://localhost:8080/pass/give (POST)  �̿�� �ֱ�
	public Pass postGive(@RequestBody Pass pass) { 
		System.out.println("******** We got POST message!    /pass/give");
		passMapper.createPass(pass);
		return pass;
	}
	
	@RequestMapping(value="/info", method = RequestMethod.POST) // http://localhost:8080/pass/info (POST)  �̿�� ��� ������
	public List<Pass> postInfo(@RequestBody User user) { 
		List<Pass> pass = new ArrayList<Pass>();
		System.out.println("******** We got POST message!    /pass/info");
		System.out.println(user.getId() + " ȸ���� �̿�� " + passMapper.selectUserPass(user));
		//pass = passMapper.selectUserPass(user); 
		//System.out.println(passMapper.selectUserPass(user));
		return passMapper.selectUserPass(user);
	}
	
	@RequestMapping(value="/use", method = RequestMethod.POST) // http://localhost:8080/pass/use (POST) �̿�� ����ϱ� // �̿�� flag Ȯ���ؼ� 
	public ResponseEntity<?> postUse(@RequestBody QRcode qrcode) { 
		
		String check = "false";
		System.out.println(qrcode.toString());
		System.out.println("******** We got POST message!   /pass/use ");

		if(passMapper.checkPassId(qrcode.getPass_id()).getFlag() == 1) {
			
			// ��밡���� �̿���� ��� -> ī�����ο��� �˸��� ������, ī�� ������ ������ ��� �Ʒ� API ������
			String token = "cNhWgJoNo0Q:APA91bG8stBCEFuUHz2urHWosC0NNi-IXK1gdW38iFk4qXJdIC7uP-bA-LDT8fNbWM8SKsOMy1uotqyQsWXmIPfG3LZDhEOmJ36lYkV5XH-ff8gcGniFtDBet9CwB7d6ydtyc8etaFYf";
			String title = "title test";
			String content = "Content Test";
			
			//FcmUtil FcmUtil = new FcmUtil();
			//FcmUtil.send_FCM(token, title, content);
			
			
			// QR�ڵ� ���� API ��� �κ�
			String requestBody = qrcode.getQr();
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		      factory.setReadTimeout(5000); // �б�ð��ʰ�, ms 
		      factory.setConnectTimeout(3000); // ����ð��ʰ�, ms 
		      HttpClient httpClient = HttpClientBuilder.create() 
		            .setMaxConnTotal(100) // connection pool ���� 
		            .setMaxConnPerRoute(5) // connection pool ���� 
		            .build(); 
		      factory.setHttpClient(httpClient); // ������࿡ ���� HttpClient ���� 
		      RestTemplate restTemplate = new RestTemplate(factory); 
		      
		      //QRpay
		      String url = "http://10.3.17.61:8080/v1/solpay/qr-payment";
		      String responseBody = restTemplate.postForObject(url, requestBody, String.class);
		      JSONParser jsonParser = new JSONParser();
		      JSONObject responseBodyJSON;
		      try {
		         responseBodyJSON = (JSONObject) jsonParser.parse(responseBody);
		         JSONObject dataHeader = (JSONObject) responseBodyJSON.get("dataHeader");
		         String successCode = (String) dataHeader.get("successCode");
		         if(successCode.equals("0"))
		         {
		            //successCode�� 0�̸� client���� �����ߴٰ� �������!
		        	 check="true";
		        	 if(true)passMapper.deletePass(qrcode.getPass_id());
		         }
		         System.out.println("successCode: "+successCode);
		      } catch (ParseException e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      }
		      // QR�ڵ� ���� API ��� ��
		}
		
		return new ResponseEntity<String>(check, (check.equals("true")? HttpStatus.OK: HttpStatus.NOT_FOUND));
	}
}

