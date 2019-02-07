package com.vivasa.qa.rest.automation.test;

import static org.testng.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.vivasa.rest.cello.restapi.CardDetailsInterface;

public class Cello {

	CardDetailsInterface spring;
	Client client;

	@BeforeMethod
	public void config() {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJsonProvider());

		spring = JAXRSClientFactory.create("http://ide1.vivasa.in:36389/", CardDetailsInterface.class, providers);
		client = WebClient.client(spring);
	}

	@Test(priority = 1)
	public void getAllStudents() throws Exception {
		Response list = spring.getAllCardDetails();

		String listOfCards = readInputStreamAsString((InputStream) list.getEntity());

		JSONParser parser = new JSONParser();
		JSONArray json = (JSONArray) parser.parse(listOfCards);

		System.out.println(json.toString());
		assertEquals(9, json.size());

	}

	@Test(priority = 2)
	public void getCardById() throws Exception {
		Response student = spring.getCardById("1");

		String StudentById = readInputStreamAsString((InputStream) student.getEntity());
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(StudentById);

		System.out.println(json.toString());
		

	}

	@Test(priority = 3)
	public void deleteCardById() {
		Response card = spring.getCardById("1");

		assertEquals(200, card.getStatus());
		

		
		



	}

	

	public static String readInputStreamAsString(InputStream in) {
		try {
			BufferedInputStream bis = new BufferedInputStream(in);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result = bis.read();
			while (result != -1) {
				byte b = (byte) result;
				buf.write(b);
				result = bis.read();
			}
			return buf.toString();
		} catch (IOException ex) {
			return null;
		}
	}

}
