package com.packt.cardatabase.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.exception.CarNotFoundException;
import com.packt.cardatabase.repositorry.CarRepository;

@RestController()
@RequestMapping("/cars")
public class CarController {

	private CarRepository carRepository;
	
	@Autowired
	public CarController(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	// Get all Cars
	@GetMapping
	public List<Car> getAllCars(){
		return carRepository.findAll();
	}
	
	// Get single Car by ID
	@GetMapping("/{carId}")
	public EntityModel<Car> getCar(@PathVariable Long carId) {
		Car car = carRepository.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));
		
		return EntityModel.of(car, 
				linkTo(methodOn(CarController.class).getCar(carId)).withSelfRel(),
				linkTo(methodOn(CarController.class).getAllCars()).withRel("cars"));
	}
	
	// Find Cars by Brand ordered by year Desc
	@GetMapping("/search/brand/{brand}")
	public List<Car> findByBrandOrderByYearDesc(@PathVariable String brand){
		return carRepository.findByBrandOrderByYearDesc(brand);
	}
	
	@PostMapping
	public Car newCar(@RequestBody Car car) {
		return carRepository.save(car);
	}
	
	// Update a Car information
	@PutMapping("/{carId}")
	public Car updateCarInfo(@RequestBody Car newCar, @PathVariable Long carId) {
		return carRepository.findById(carId)
				.map(car -> {
					car.setBrand(newCar.getBrand());
					car.setColor(newCar.getColor());
					car.setDescription(newCar.getDescription());
					car.setModel(newCar.getModel());
					car.setOwner(newCar.getOwner());
					car.setPrice(newCar.getPrice());
					car.setRegisterNumber(newCar.getRegisterNumber());
					car.setYear(newCar.getYear());
					return carRepository.save(car);
				})
				.orElseGet(() -> {
					newCar.setCarId(carId);
					return carRepository.save(newCar);
				});
	}
	
	// Delete a Car
	@DeleteMapping("/{carId}")
	public void deleteCar(@PathVariable Long carId) {
		carRepository.deleteById(carId);
	}
	
}
