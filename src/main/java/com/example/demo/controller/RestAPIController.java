package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.example.demo.converter.j_to_x;
import com.example.demo.converter.x_to_j;
import com.github.asilvestre.jpurexml.XmlParseException;


@RestController
public class RestAPIController {

	
	
	@RequestMapping(value = "/xml2json", method = RequestMethod.POST, consumes = "application/xml", produces = "application/json")
	public ResponseEntity<String> xtoj(HttpServletRequest request, @RequestBody String xml) throws XmlParseException, ParserConfigurationException, SAXException, IOException {


		String json="";
		if(XMLcheck(xml)) {
			
			if(xml.contains("<?")) {// 헤더에 형식이 들어가있으며 
				//System.out.println(xml.indexOf("<?")+1); -1반환해서
				xml=xml.substring(xml.indexOf("?>")+2);
				System.out.println(XMLcheck(xml)+"\n"+ xml);
				xml = "<root>"+ xml+"</root>";
				json = x_to_j.xml2json(xml);//변환
				
				System.out.println(json);
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}else {
				xml = "<root>"+ xml+"</root>";
				json = x_to_j.xml2json(xml);//변환
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		}else {
			
			return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
		}
		
				
	}

	@RequestMapping(value = "/json2xml", method = RequestMethod.POST, consumes = "application/json", produces = "application/xml")
	public ResponseEntity<String> jtox(HttpServletRequest request, @RequestBody String json) throws JSONException {

		String output="";
		
		//System.out.println(json.substring(0,1)+"    "+json.substring(json.length()-1));
		System.out.println(json.substring(json.length()-1));
		
		if(JSONUtils.isJSONValid(json) && !(j_to_x.json2xml(json).equals("")) && LengCheck(json)) {
			output = j_to_x.json2xml(json);
			
			output = "<root>" + output + "</root>";

			return new ResponseEntity<String>(output, HttpStatus.OK);
		}
		else {
			System.out.println("체크2 : "+JSONUtils.isJSONValid(json));
			return new ResponseEntity<String>(output, HttpStatus.BAD_REQUEST);
		}	
		
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
	
	//-------------
	public boolean XMLcheck(String x) {
		if(x.contains("<") && x.contains(">") && x.contains("</")) {
			return true;
		}
		else {
			return false;
		}		
	}

	public boolean LengCheck(String json) {
		if(json.substring(json.length()-1).equals("}")) {
			return true;
		}else {
			return false;
		}
	}
}
