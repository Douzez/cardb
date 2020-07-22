package com.packt.cardatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.User;
import com.packt.cardatabase.repositorry.CarRepository;
import com.packt.cardatabase.repositorry.OwnerRepository;
import com.packt.cardatabase.repositorry.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CardatabaseApplication {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		log.info("... CarDatabase initialized.");
	}
	
	@Bean
	CommandLineRunner runner() {
		log.info("Inserting DUMMY data...");
		return args -> {
			Owner mar = ownerRepository.save(new Owner("Margaret", "Cruz"));
			Owner fer = ownerRepository.save(new Owner("Fernando", "Cruz"));
			Owner bea = ownerRepository.save(new Owner("Beatriz", "Valdez"));
			Owner rog = ownerRepository.save(new Owner("Rogelio", "Ruiz"));
			Owner mari = ownerRepository.save(new Owner("Marie", "Jeager"));
			Owner marie = ownerRepository.save(new Owner("Marie", "Bouvoir"));
			/*
			List<Owner> ownersOrderByLN = ownerRepository.findByFirstNameOrderByLastNameAsc("Marie");
			ownersOrderByLN.forEach(owner -> log.info(owner.toString()));
			
			List<Owner> ownersOrderByFN = ownerRepository.findByLastNameOrderByFirstNameAsc("Cruz");
			ownersOrderByFN.forEach(owner -> log.info(owner.toString()));
			
			List<Owner> ownerByAnyFirstName = ownerRepository.findByFirstName("o");
			ownerByAnyFirstName.forEach(owner -> log.info(owner.toString()));
			*/
			carRepository.save(
					new Car("Mazda", "Mazda2", "Gray", "729", 2012, 10000, "The best car in the world", mar));
			carRepository.save(
					new Car("Mazda", "Mazda3", "Black", "179", 2018, 30000, "Hatchback", fer));
			carRepository.save(
					new Car("Nissan", "Sentra", "Blue", "182", 2019, 30000, "Good sedan", marie));
			carRepository.save(
					new Car("Volkswagen", "Bocho", "Light blue", "129", 2000, 5000, "The best car in the world", bea));
			carRepository.save(
					new Car("Honda", "Civic", "Black", "912", 2017, 40000, "A really fast car", rog));
			/*
			List<Car> cars = carRepository.findByBrand("Mazda");
			cars.forEach(car -> log.info(car.toString()));
			
			List<Car> carsOrderBy = carRepository.findByBrandOrderByYearDesc("Mazda");
			carsOrderBy.forEach(car -> log.info(car.toString()));
			
			List<Car> carsEndsN = carRepository.findByBrandEndsWith("n");
			carsEndsN.forEach(car -> log.info(car.toString()));
			
			List<Car> carsEndsA = carRepository.findByBrandEndsWith("a");
			carsEndsA.forEach(car -> log.info(car.toString()));
			*/
			
			// username: user password: user
			userRepository.save(new User("user", 
					"$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi",
					"USER"));
			// username: admin password: admin
			userRepository.save(new User("admin",
		    		"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", 
		    		"ADMIN"));
			
			log.info("... DUMMY data inserted.");
		};
	}

}
