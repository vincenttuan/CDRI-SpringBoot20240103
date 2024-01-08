package com.example.psi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.po.Department;
import com.example.psi.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void add(DepartmentDTO departmentDTO) {
		// 將 departmentDTO 轉 department
		Department department = modelMapper.map(departmentDTO, Department.class);
		departmentRepository.save(department);
	}
	
}
