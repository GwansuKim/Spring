package com.yedam.boot.emp;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Body {
	
//	public List<Plant> item;
	public List<Item> items;
//	public int numOfRows;
//	public int pageNo;
//	public int totalCount;
}
