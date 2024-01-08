package com.example.psi.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.dto.EmployeeDTO;
import com.example.psi.model.dto.EmployeePageDTO;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void add(EmployeeDTO employeeDTO) {
		// employeeDTO 轉 employee
		Employee employee = modelMapper.map(employeeDTO, Employee.class);
		employeeRepository.save(employee);
	}
	
	public void add(EmployeeDTO employeeDTO, Long departmentId) {
		// employeeDTO 轉 employee
		Employee employee = modelMapper.map(employeeDTO, Employee.class);
		// 根據指定 departmentId 注入 department 物件
		Department department = departmentRepository.findById(departmentId).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
	}
	
	// 全部查詢(分頁)
	public EmployeePageDTO findAll(Pageable pageable) {
		Page<Employee> empPage = employeeRepository.findAll(pageable);
		Page<EmployeeDTO> empPageDTO = empPage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
		return new EmployeePageDTO(empPageDTO);
	}
}
