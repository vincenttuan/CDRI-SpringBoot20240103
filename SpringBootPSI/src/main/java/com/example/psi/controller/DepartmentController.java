package com.example.psi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.psi.model.dto.DepartmentDTO;
import com.example.psi.service.DepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/")
	public String index(@ModelAttribute DepartmentDTO departmentDTO, Model model) {
		List<DepartmentDTO> departments = departmentService.findAll();
		model.addAttribute("departments", departments);
		return "department";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
		model.addAttribute("", departmentDTO)
		return "department";
	}
	
	@PostMapping("/")
	public String add(DepartmentDTO departmentDTO) {
		departmentService.add(departmentDTO);
		return "redirect:/department/";
	}
	
}
