package com.packt.cardatabase.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.packt.cardatabase.domain.Car;

@Component
public class CarModelAssembler implements RepresentationModelAssembler<Car, EntityModel<Car>> {

	@Override
	public EntityModel<Car> toModel(Car car) {
		return EntityModel.of(car,
				linkTo(methodOn(CarController.class).getCar(car.getCarId())).withSelfRel(),
				linkTo(methodOn(CarController.class).getAllCars()).withRel("cars"));
	}
	
	
}
