package com.packt.cardatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Car {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long carId;
	
	private String brand, model, color, registerNumber;
	private int year, price;
	
	@Column(name="explanation", nullable=false, length=512)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private Owner owner;
	
	public Car() {}

	public Car(String brand, String model, String color, String registerNumber, 
			int year, int price, String description, Owner owner) {
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.year = year;
		this.price = price;
		this.description = description;
		this.owner = owner;
	}
	
}
