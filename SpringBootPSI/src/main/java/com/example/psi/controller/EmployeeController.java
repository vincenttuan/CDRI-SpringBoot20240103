package com.example.psi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.model.dto.EmployeeDTO;
import com.example.psi.model.dto.EmployeePageDTO;
import com.example.psi.model.po.Employee;
import com.example.psi.service.DepartmentService;
import com.example.psi.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmployeeService employeeService;
	
	// page=頁數&size=每頁筆數
	// 例如: page=0&size=5 第 1 頁每頁 5 筆
	// 例如: page=1&size=5 第 2 頁每頁 5 筆
	// 例如: page=2&size=5 第 3 頁每頁 5 筆
	@GetMapping("/")
	public String index(@RequestParam(name = "page", defaultValue = "0") int page, 
						@RequestParam(name = "size", defaultValue = "3") int size,
						@ModelAttribute EmployeeDTO employeeDTO, Model model) {
		Pageable pageable = PageRequest.of(page, size);
		List<DepartmentDTO> departments = departmentService.findAll();
		EmployeePageDTO employeePageDTO = employeeService.findAll(pageable); // 得到該分頁的數據實體
		model.addAttribute("departments", departments);
		model.addAttribute("employeePageDTO", employeePageDTO);
		return "employee";
	}
	
	@PostMapping("/")
	public String create(EmployeeDTO employeeDTO) {
		employeeService.add(employeeDTO);
		return "redirect:/employee/";
	}
	
	@GetMapping("/edit/{id}") // 修改頁面的呈現
	public String edit(@PathVariable("id") Long id, Model model) {
		EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employeeDTO);
		model.addAttribute("departments", departmentService.findAll());
		return "employee-edit";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable("id") Long id, EmployeeDTO employeeDTO) {
		employeeService.update(employeeDTO, id);
		return "redirect:/employee/";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		employeeService.delete(id);
		return "redirect:/employee/";
	}
}
