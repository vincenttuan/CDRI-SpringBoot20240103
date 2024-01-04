package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.StudentScore;
import com.example.demo.repository.StudentScoreRepository;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;



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
		
		// 計算前 25% 的平均(高標)
		int top25Count = (int)(sortedScores.size() * 0.25);
		double top25AverageScore = sortedScores.stream()
				.limit(top25Count)
				.mapToDouble(StudentScore::getAverageScore)
				.average()
				.orElse(0.0);
		
		// 計算總平均(低標)
		double averageScore = scores.stream()
				.mapToDouble(StudentScore::getAverageScore)
				.average()
				.orElse(0.0);
		
		model.addAttribute("scores", sortedScores);
		model.addAttribute("top25AverageScore", top25AverageScore);
		model.addAttribute("averageScore", averageScore);
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
			//studentScore.updateTotalAndAverage();
			studentScoreRepository.save(studentScore);
		}
		return "add 100 OK";
	}
	
	@PostMapping("/")
	@ResponseBody
	public String add(StudentScore studentScore) {
		//studentScore.updateTotalAndAverage();
		studentScoreRepository.save(studentScore);
		return "Add OK: " + studentScore;
	}
	
	
	@PutMapping("/{id}")
	@ResponseBody
	public String update(@PathVariable("id") Integer id, StudentScore uptStudentScore) {
		// 根據 id 找到該筆資料
		Optional<StudentScore> studentScoreOpt = studentScoreRepository.findById(id);
		if(studentScoreOpt.isPresent()) {
			StudentScore studentScore = studentScoreOpt.get();
			
			// "id", "totalScore", "averageScore" 不要複製, 其餘都要複製
			BeanUtils.copyProperties(uptStudentScore, studentScore, "id", "totalScore", "averageScore");
			//studentScore.updateTotalAndAverage();
			// 修改更新
			studentScoreRepository.saveAndFlush(studentScore);
			
			return "Update OK";
		}
		return "Update Fail";
		
	}
	
	@PutMapping("/update_name/{id}")
	@ResponseBody
	public String updateName(@PathVariable("id") Integer id, @RequestParam("name") String name) {
		studentScoreRepository.updateNameById(id, name);
		return "Update Name OK";
	}
	
	
	
}
