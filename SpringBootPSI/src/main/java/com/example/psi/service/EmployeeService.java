package com.example.psi.service;

import java.util.Optional;

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
	
	// 新增 I
	public void add(EmployeeDTO employeeDTO) {
		// employeeDTO 轉 employee
		Employee employee = modelMapper.map(employeeDTO, Employee.class);
		// 根據指定 employeeDTO 的 department's Id 注入 department 物件
		Department department = departmentRepository.findById(employeeDTO.getDepartment().getId()).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
	}
	
	// 新增 II
	public void add(EmployeeDTO employeeDTO, Long departmentId) {
		// employeeDTO 轉 employee
		Employee employee = modelMapper.map(employeeDTO, Employee.class);
		// 根據指定 departmentId 注入 department 物件
		Department department = departmentRepository.findById(departmentId).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
	}
	
	// 修改
	public void update(EmployeeDTO employeeDTO, Long id) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		if(employeeOpt.isPresent()) {
			Employee employee = employeeOpt.get();
			employee.setName(employeeDTO.getName()); // 更新員工姓名
			Department department = departmentRepository.findById(employeeDTO.getDepartment().getId()).get(); // 找到員工部門
			employee.setDepartment(department); // 更新員工部門
			employeeRepository.save(employee);
		} 
	}
	
	// 刪除
	public void delete(Long id) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		if(employeeOpt.isPresent()) {
			employeeRepository.delete(employeeOpt.get());
		} 
	}
	
	// 查詢單筆
	public EmployeeDTO getEmployeeById(Long id) {
		Optional<Employee> employeetOpt = employeeRepository.findById(id);
		if(employeetOpt.isPresent()) {
			Employee employee = employeetOpt.get();
			EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
			return employeeDTO;
		}
		return null;
	}
	
	// 全部查詢(分頁)
	public EmployeePageDTO findAll(Pageable pageable) {
		Page<Employee> empPage = employeeRepository.findAll(pageable);
		Page<EmployeeDTO> empPageDTO = empPage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
		return new EmployeePageDTO(empPageDTO);
	}
}
