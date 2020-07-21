package com.packt.cardatabase.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
	private CarModelAssembler assembler;
	
	@Autowired
	public CarController(CarRepository carRepository, CarModelAssembler assembler) {
		this.carRepository = carRepository;
		this.assembler = assembler;
	}
	
	// Get all Cars
	@GetMapping
	public CollectionModel<EntityModel<Car>> getAllCars(){
		List<EntityModel<Car>> entityList = carRepository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(entityList,
				linkTo(methodOn(CarController.class).getAllCars()).withRel("cars"));
	}
	
	// Get single Car by ID
	@GetMapping("/{carId}")
	public EntityModel<Car> getCar(@PathVariable Long carId) {
		Car car = carRepository.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));
		
		return assembler.toModel(car);
	}
	
	// Find Cars by Brand ordered by year Desc
	@GetMapping("/search/brand/{brand}")
	public CollectionModel<EntityModel<Car>> findByBrandOrderByYearDesc(@PathVariable String brand){
		
		List<EntityModel<Car>> entityList = 
				carRepository.findByBrandOrderByYearDesc(brand).stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(entityList);
	}
	
	@PostMapping
	public ResponseEntity<?> newCar(@RequestBody Car car) {
		
		EntityModel<Car> entityModel = assembler.toModel(carRepository.save(car));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	// Update a Car information
	@PutMapping("/{carId}")
	public ResponseEntity<?> updateCarInfo(@RequestBody Car newCar, @PathVariable Long carId) {
		Car updatedCar = carRepository.findById(carId)
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
		
		EntityModel<Car> entityModel = assembler.toModel(updatedCar);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	// Delete a Car
	@DeleteMapping("/{carId}")
	public ResponseEntity<?> deleteCar(@PathVariable Long carId) {
		carRepository.deleteById(carId);
		
		return ResponseEntity.noContent().build();
	}
	
}
