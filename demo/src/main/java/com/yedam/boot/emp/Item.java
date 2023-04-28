package com.yedam.boot.emp;

import java.util.List;

import lombok.Data;

@Data
public class Item {

	public int numOfRows;
	public int pageNo;
	public int totalCount;
	public List<Plant> item;
}
