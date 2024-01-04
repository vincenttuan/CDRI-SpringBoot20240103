package com.example.psi.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders") // 因為 MySQL 不可以建立 Order 資料表 (因 Order 在 MySQL 中是保留字)
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 訂單序號
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date; // 訂單日期
}
