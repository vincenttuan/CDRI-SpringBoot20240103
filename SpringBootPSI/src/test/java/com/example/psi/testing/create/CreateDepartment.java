package com.example.psi.testing.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.po.Department;
import com.example.psi.service.DepartmentService;

@SpringBootTest
public class CreateDepartment {
	@Autowired
	private DepartmentService departmentService;
	
	// 模擬 controller 新增部門
	@Test
	public void addTest() {
		DepartmentDTO d1 = new DepartmentDTO();
		d1.setName("業務部");
		
		DepartmentDTO d2 = new DepartmentDTO();
		d2.setName("資訊部");
		
		DepartmentDTO d3 = new DepartmentDTO();
		d3.setName("採購部");
		
		// 儲存
		departmentService.add(d1);
		departmentService.add(d2);
		departmentService.add(d3);
		
		System.out.println("Save OK !");
	}
	
}
