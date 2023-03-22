package com.yedam.app.emp.service;

import lombok.Data;

@Data
public class EmpVO {
	String employee_id;
	String first_name;
	String last_name;
	String email;
	String phone_number;
	String hire_date;
	String job_id;
	String salary;
	String commission_pct;
	String manager_id;
	String department_id;
}
