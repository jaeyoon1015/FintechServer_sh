package com.example.demo;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("user") // http://localhost:8080/user로 매핑 


public class UserController {
	
	@Autowired
	UserMapper userMapper;

	@RequestMapping(method = RequestMethod.GET) // http://localhost:8080/user (GET)
	public String getMessages() {
		System.out.println("################  We got GET message");
		return "hello";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> postMessages(@RequestBody User user) { // http://localhost:8080/user (POST)
		System.out.println("******** We got POST message! USER ID: " + user.getId() + " USER PW: " + user.getPw() );
		//userMapper.create(user);
		//userMapper.select().forEach(c -> System.out.println(c.getId() + c.getPw()));
		
		String check = "NULL";
		if(userMapper.check(user)==null){
			System.out.println("존재하지 않는 회원이 로그인을 시도하고 있다"); // 존재하지 않는 회원일 때
			check = "NO";
		}else {
			System.out.println("존재하는 회원임");
			check = "YES";
		}
		return new ResponseEntity<String>(check, HttpStatus.OK);
		//return user;
	}
	 
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public String postSignup(@RequestBody User user){
	   System.out.println("******** We got POST message!    /user/signup");
	   userMapper.create(user);
	   return "yes";
	 }
	

}
