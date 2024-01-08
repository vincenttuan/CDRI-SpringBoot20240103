package com.example.psi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psi.model.dto.EmployeeDTO;
import com.example.psi.model.po.Department;
import com.example.psi.model.po.Employee;
import com.example.psi.repository.DepartmentRepository;
import com.example.psi.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void add(EmployeeDTO employeeDTO) {
		// employeeDTO 轉 employee
		Employee employee = new Employee();
		employee.setName(employeeDTO.getName());
		Department department = departmentRepository.findById(employeeDTO.getDepartmentDTO().getId()).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
	}
	
	public void add(EmployeeDTO employeeDTO, Long departmentId) {
		// employeeDTO 轉 employee
		Employee employee = new Employee();
		employee.setName(employeeDTO.getName());
		Department department = departmentRepository.findById(departmentId).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
	}
	
	
	
}
