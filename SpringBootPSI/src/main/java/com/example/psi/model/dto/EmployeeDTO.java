package com.example.psi.model.dto;

import com.example.psi.model.po.Department;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
	
	private Long id; // 員工序號
	private String name; // 員工姓名
	
	private DepartmentDTO department;
}
