package com.example.psi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.po.Department;
import com.example.psi.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public void add(DepartmentDTO departmentDTO) {
		// 將 departmentDTO 轉 department
		Department department = new Department();
		department.setName(departmentDTO.getName());
		departmentRepository.save(department);
	}
	
}
