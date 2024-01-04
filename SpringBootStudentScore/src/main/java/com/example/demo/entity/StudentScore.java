package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@Entity // 預設 @Entity(name = "student_score")
public class StudentScore {
	@Id // 主鍵
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//@Column(name = "name", length = 255, nullable = true, unique = false) // 預設值可以不寫
	private String name;
	
	//@Column(columnDefinition = "Integer default 0")
	private Integer chineseScore;
	
	//@Column(columnDefinition = "Integer default 0")
	private Integer englishScore;
	
	//@Column(columnDefinition = "Integer default 0")
	private Integer mathScore;
	
	//@Column(columnDefinition = "Integer default 0")
	private Integer totalScore;
	
	//@Column(columnDefinition = "Double default 0")
	private Double averageScore;
	
	@PreUpdate
	@PrePersist
	// 更新總分與平均
	public void updateTotalAndAverage() {
		setTotalScore(chineseScore + englishScore + mathScore);
		setAverageScore(totalScore / 3.0);
	}
}
