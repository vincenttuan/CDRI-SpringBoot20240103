package com.example.psi.testing.read;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.dto.EmployeeDTO;
import com.example.psi.model.po.Department;
import com.example.psi.repository.DepartmentRepository;
import com.example.psi.service.DepartmentService;
import com.example.psi.service.EmployeeService;

import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;

@SpringBootTest
public class ReadEmployee {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Transactional
	@Test
	public void readAll() {
		System.out.println("readOne:");
		List<EmployeeDTO> employeeDTOs = employeeService.findAll();
		for(EmployeeDTO employeeDTO : employeeDTOs) {
			System.out.println("id: " + employeeDTO.getId());
			System.out.println("name: " + employeeDTO.getName());
			System.out.println("department: " + employeeDTO.getDepartment());
		}
	}
	
}
