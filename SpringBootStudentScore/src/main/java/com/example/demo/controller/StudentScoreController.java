package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.StudentScore;
import com.example.demo.repository.StudentScoreRepository;
import com.github.javafaker.Faker;



@Controller
@RequestMapping("/student-score")
public class StudentScoreController {
	
	@Autowired
	private StudentScoreRepository studentScoreRepository;
	
	@GetMapping("/")
	public String index(Model model) {
		List<StudentScore> scores = studentScoreRepository.findAll();
		// 根據總分進行降序排序
		List<StudentScore> sortedScores = scores.stream()
				.sorted(Comparator.comparingInt(StudentScore::getTotalScore).reversed())
				.collect(Collectors.toList());
		
		model.addAttribute("scores", sortedScores);
		return "student_score";
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
	
	// 產生 100 筆測試資料
	@GetMapping("/add/100")
	@ResponseBody
	public String add100() {
		Faker faker = new Faker();
		Random random = new Random();
		for(int i=0;i<100;i++) {
			StudentScore studentScore = new StudentScore();
			studentScore.setName(faker.name().fullName());
			studentScore.setChineseScore(random.nextInt(101));
			studentScore.setEnglishScore(random.nextInt(101));
			studentScore.setMathScore(random.nextInt(101));
			studentScore.updateTotalAndAverage();
			studentScoreRepository.save(studentScore);
		}
		return "add 100 OK";
	}
	
	@PostMapping("/")
	@ResponseBody
	public String add(StudentScore studentScore) {
		studentScore.updateTotalAndAverage();
		studentScoreRepository.save(studentScore);
		return "Add OK: " + studentScore;
	}
	
	
	
}
