package com.yedam.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	@RequestMapping("/getEmpInfo")
	@ResponseBody			//return값을 response body에 출력
	public EmpVO getEmpInfo(EmpVO empVO) {
		return empService.getEmpInfo(empVO);
	}
}
