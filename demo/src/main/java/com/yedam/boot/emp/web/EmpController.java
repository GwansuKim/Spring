package com.yedam.boot.emp.web;

import java.io.File;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.api.WstxOutputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yedam.boot.emp.FileNameModel;
import com.yedam.boot.emp.Plant;
import com.yedam.boot.emp.ResponsePlant;
import com.yedam.boot.emp.mapper.EmpMapper;

@Controller
public class EmpController {

	@Autowired
	EmpMapper mapper;

//	@GetMapping("/")
//	@ResponseBody
//	public String main() {
//		return "hello";
//	}

	// 사원 전체조회
	@GetMapping("/getAllEmp")
	public String getAllEmp(Model model) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		
		List<Plant> list = mapper.getAllPlant();
		for(Plant temp : list) {
			ResponseEntity<String> response = restTemplate.exchange(URI.create(
					"http://api.nongsaro.go.kr/service/garden/gardenFileList?apiKey=20230426FGRJ3HQO0UAETES4TYIW&cntntsNo=" + temp.getCntntsNo()),
					HttpMethod.GET, entity, String.class);
			
			System.out.println(temp);
			List<Plant> list2 = this.parser(response.getBody()).getBody().getItems().get(0).getItem();
			for(Plant temp2 : list2) {
				mapper.insertPlantImg(temp2);
			}
			
		}

		model.addAttribute("empList", mapper.selectEmpAllList());
		return "empList";
	}
	
	@GetMapping("/temp")
	public String temp() {
		return "temp";
	}
	
	@PostMapping("/test")
	@ResponseBody
	public String test(@RequestParam("image") MultipartFile multi,  HttpServletRequest request, HttpServletResponse response) {
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src\\main\\resources\\static\\images\\";
		String url = null;
		String mv = "";
		
		try {
			String uploadPath = path;
			String originFilename = multi.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
			long size = multi.getSize();
			FileNameModel fileNameModel = new FileNameModel();
			String saveFileName = fileNameModel.GenSaveFileName(extName);
			
			if(!multi.isEmpty()) {
				File file = new File(uploadPath, saveFileName);
				multi.transferTo(file);

				mv = "{\"filename\":\"" + saveFileName + "\"}";
				
			} else {
				mv = "fail";
			}
		} catch (Exception e) {
			System.out.println("[Error] " + e.getMessage());
		}
		return mv;
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
