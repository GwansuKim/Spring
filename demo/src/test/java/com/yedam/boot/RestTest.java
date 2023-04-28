package com.yedam.boot;

import java.net.URI;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.api.WstxOutputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yedam.boot.emp.ResponsePlant;
import com.yedam.boot.emp.mapper.EmpMapper;

public class RestTest {
	
	@Autowired
	EmpMapper mapper;
	
	@Test
	public void plantInfoGet() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(URI.create(
				"http://api.nongsaro.go.kr/service/garden/gardenFileList?apiKey=20230426FGRJ3HQO0UAETES4TYIW&cntntsNo=12938"),
				HttpMethod.GET, entity, String.class);
		System.out.println(this.parser(response.getBody()).getBody().getItems().get(0).getItem().get(5).getRtnFileUrl() );
	}
	
	public ResponsePlant parser(String xml) {
		XMLInputFactory ifactory = new WstxInputFactory(); // Woodstox XMLInputFactory impl
		ifactory.setProperty(WstxInputProperties.P_MAX_ATTRIBUTE_SIZE, 320000);
		// configure

		XMLOutputFactory ofactory = new WstxOutputFactory(); // Woodstox XMLOutputfactory impl
		ofactory.setProperty(WstxOutputProperties.P_OUTPUT_CDATA_AS_TEXT, true);

		XmlFactory xf = XmlFactory.builder().xmlInputFactory(ifactory) // note: in 2.12 and before "inputFactory()"
				.xmlOutputFactory(ofactory) // note: in 2.12 and before "outputFactory()"
				.build();
		XmlMapper mapper = new XmlMapper(xf).builder().defaultUseWrapper(false)
				// enable/disable Features, change AnnotationIntrospector
				.build(); // there are other overloads too

		ObjectMapper xmlMapper = mapper;
		ResponsePlant response = null;
		try {
			response = xmlMapper.readValue(xml, ResponsePlant.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}
}
