package com.yedam.boot.emp;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "response")
@Data
public class ResponsePlant {
	
	@XmlElement(name = "header")
	public ResponseHeader header;
	
	@XmlElement(name = "body")
	public Body body;

}
