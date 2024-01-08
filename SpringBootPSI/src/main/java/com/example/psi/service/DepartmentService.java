package com.example.psi.service;

import java.util.List;
import java.util.Optional;

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
	
	public DepartmentDTO getDepartmentById(Long id) {
		Optional<Department> departmentOpt = departmentRepository.findById(id);
		if(departmentOpt.isPresent()) {
			Department department = departmentOpt.get();
			// department 轉 departmentDTO
			DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
			return departmentDTO;
		}
		return null;
	}
	
	public DepartmentDTO[] findAll() {
		List<Department> departments = departmentRepository.findAll();
		DepartmentDTO[] departmentDTOs = modelMapper.map(departments, DepartmentDTO[].class);
		return departmentDTOs;
	}
	
}
