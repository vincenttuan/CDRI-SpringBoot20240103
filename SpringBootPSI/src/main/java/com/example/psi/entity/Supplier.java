package com.example.psi.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "supplier")
@Data
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 供應商序號
	
	@Column
	private String name; // 供應商名稱
	
	@OneToMany(mappedBy = "supplier")
	@OrderBy("id ASC")
	private Set<Purchase> purchases = new LinkedHashSet<>();
	
}
