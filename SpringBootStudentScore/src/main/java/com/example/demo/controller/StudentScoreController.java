package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.StudentScore;
import com.example.demo.repository.StudentScoreRepository;

@Controller
@RequestMapping("/student-score")
public class StudentScoreController {
	
	@Autowired
	private StudentScoreRepository studentScoreRepository;
	
	@GetMapping("/")
	@ResponseBody
	public String index() {
		return studentScoreRepository.findAll().toString();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public String getOne(@PathVariable("id") Integer id) {
		Optional<StudentScore> studentScoreOpt = studentScoreRepository.findById(id);
		if(studentScoreOpt.isPresent()) {
			return studentScoreOpt.get() + "";
		}
		return "";
	}
	
	@PostMapping("/")
	@ResponseBody
	public String add(StudentScore studentScore) {
		studentScore.updateTotalAndAverage();
		studentScoreRepository.save(studentScore);
		return "Add OK: " + studentScore;
	}
	
}
