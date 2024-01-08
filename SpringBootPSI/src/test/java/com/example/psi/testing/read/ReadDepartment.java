package com.example.psi.testing.read;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.service.DepartmentService;

@SpringBootTest
public class ReadDepartment {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Test
	public void readOne() {
		DepartmentDTO departmentDTO = departmentService.getDepartmentById(1L);
		System.out.println(departmentDTO.getId());
		System.out.println(departmentDTO.getName());
	}
}
