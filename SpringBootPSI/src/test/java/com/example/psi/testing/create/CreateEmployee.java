package com.example.psi.testing.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.psi.model.dto.EmployeeDTO;
import com.example.psi.service.EmployeeService;

@SpringBootTest
public class CreateEmployee {
	@Autowired
	private EmployeeService employeeService;
	
	// 模擬 controller 新增員工
	@Test
	public void addTest() {
		
		EmployeeDTO emp1 = new EmployeeDTO();
		emp1.setName("劉一");
		
		EmployeeDTO emp2 = new EmployeeDTO();
		emp2.setName("陳二");
		
		EmployeeDTO emp3 = new EmployeeDTO();
		emp3.setName("張三");
		
		EmployeeDTO emp4 = new EmployeeDTO();
		emp4.setName("李四");
		
		EmployeeDTO emp5 = new EmployeeDTO();
		emp5.setName("王五");
		
		EmployeeDTO emp6 = new EmployeeDTO();
		emp6.setName("趙六");
		
		EmployeeDTO emp7 = new EmployeeDTO();
		emp7.setName("孫七");
		
		EmployeeDTO emp8 = new EmployeeDTO();
		emp8.setName("周八");
		
		EmployeeDTO emp9 = new EmployeeDTO();
		emp9.setName("吳九");
		
		EmployeeDTO emp10 = new EmployeeDTO();
		emp10.setName("鄭十");
		
		// 儲存
		employeeService.add(emp1, 1L);
		employeeService.add(emp2, 2L);
		employeeService.add(emp3, 2L);
		employeeService.add(emp4, 3L);
		employeeService.add(emp5, 3L);
		employeeService.add(emp6, 3L);
		employeeService.add(emp7, 1L);
		employeeService.add(emp8, 2L);
		employeeService.add(emp9, 3L);
		employeeService.add(emp10, 3L);
		
		System.out.println("Save OK !");
	}
	
}
