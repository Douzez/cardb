package com.packt.cardatabase.repositorry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.packt.cardatabase.domain.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

	// Fetch cars by brand
	List<Car> findByBrand(String brand);
	
	// Fetch cars by color
	List<Car> findByColor(String color);
	
	// Fetch cars by year
	List<Car> findByYear(int year);
	
	// Fetch cars by brand order by year
	List<Car> findByBrandOrderByYearDesc(String brand);
	
	// Fetch cars by brand using like
	@Query("Select c from Car c where c.brand like %?1")
	List<Car> findByBrandEndsWith(String brand);
	
}
