package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.xml2json.ConverterConfig;
import com.example.demo.xml2json.JsonConverter;
import com.github.asilvestre.jpurexml.XmlParseException;


@RestController
public class RestAPIController {

	
	
	@RequestMapping(value = "/xml2json", method = RequestMethod.POST, consumes = "application/xml", produces = "application/json")
	public ResponseEntity<String> xtoj(HttpServletRequest request, @RequestBody String xml) throws XmlParseException {

//		System.out.println("xml2json success \n");
//		System.out.println(xml);
//		String json = JsonConverter.convertXml(xml);
//		System.out.println(json);
		System.out.println(x_to_j.xml2json(xml));
		String json = x_to_j.xml2json(xml);
		return new ResponseEntity<String>(x_to_j.xml2json(xml), HttpStatus.OK);
	}

	@RequestMapping(value = "/json2xml", method = RequestMethod.POST, consumes = "application/json", produces = "application/xml")
	public ResponseEntity<String> jtox(HttpServletRequest request, @RequestBody String json) throws JSONException {
		
		String xml = "<? xml version=\"1.0\" encoding=\"utf-8\" ?>\n";
		//j_to_x.json2xml(json);
		//JSONObject json1 = new JSONObject(json);
		//xml += XML.toString(json1);
		//String bb = xml;
		//System.out.println("json2xml success \n" + xml);
		
		return new ResponseEntity<String>(j_to_x.json2xml(json), HttpStatus.OK);
		}

	
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<String, Object>> status() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "success");

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/xml")
	public ResponseEntity<String> uploadJsonFile(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws IOException {
		String json = new String(file.getBytes());
		System.out.println("uploadJsonFile success \n" + json);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
