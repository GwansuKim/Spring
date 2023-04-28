package com.yedam.boot.emp;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "header")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ResponseHeader {
	
	@XmlAttribute(name = "resultCode")
	String resultCode;
	@XmlAttribute(name = "resultMsg")
	String resultMsg;
	@XmlAttribute(name = "requestParameter")
	String requestParameter;

}
