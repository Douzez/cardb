package com.packt.cardatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true) 
	private String username;
	
	@Column(nullable = false)
	private String password, role;
	
	public User() { }

	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
}
