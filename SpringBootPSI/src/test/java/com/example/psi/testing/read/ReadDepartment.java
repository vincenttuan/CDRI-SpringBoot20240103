package com.example.psi.testing.read;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.po.Department;
import com.example.psi.repository.DepartmentRepository;
import com.example.psi.service.DepartmentService;

import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;

@SpringBootTest
public class ReadDepartment {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	//@Transactional
	//@Test
	public void readOne() {
		System.out.println("readOne:");
		DepartmentDTO departmentDTO = departmentService.getDepartmentById(1L);
		System.out.println("id: " + departmentDTO.getId());
		System.out.println("name: " + departmentDTO.getName());
		System.out.println("employee size: " + departmentDTO.getEmployees().size());
	}
	
	//@Transactional // 在測試的時候若 fetch = FetchType.LAZY, 要加上 @Transactional
	//@Test
	public void readOne2() {
		System.out.println("readOne2:");
		Department department = departmentRepository.findById(1L).get();
		System.out.println("id: " + department.getId());
		System.out.println("name: " + department.getName());
		System.out.println("employee size: " + department.getEmployees().size());
	}
	
	@Test
	public void readAll() {
		System.out.println("readAll:");
		List<DepartmentDTO> departmentDTOs = departmentService.findAll();
		for(DepartmentDTO departmentDTO : departmentDTOs) {
			System.out.println("id: " + departmentDTO.getId());
			System.out.println("name: " + departmentDTO.getName());
			System.out.println("employee size: " + departmentDTO.getEmployees().size());
		}
	}
}
