package com.example.psi.testing.read;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.dto.EmployeeDTO;
import com.example.psi.model.dto.EmployeePageDTO;
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
		Pageable pageable = PageRequest.of(0, 3);
		EmployeePageDTO employeePageDTO = employeeService.findAll(pageable); // 得到該分頁的數據實體
		for(EmployeeDTO employeeDTO : employeePageDTO.getEmployees()) {
			System.out.println("id: " + employeeDTO.getId());
			System.out.println("name: " + employeeDTO.getName());
			System.out.println("department: " + employeeDTO.getDepartment().getName());
		}
	}
	
}
