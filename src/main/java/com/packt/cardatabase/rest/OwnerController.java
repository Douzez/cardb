package com.packt.cardatabase.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.exception.OwnerNotFoundException;
import com.packt.cardatabase.repositorry.OwnerRepository;

@RestController
@RequestMapping("/owners")
public class OwnerController {
	
	// TODO: IMPLEMENT ResponseEntity<?> in all methods to get a proper response
	
	private OwnerRepository ownerRepository;
	private OwnerModelAssembler assembler;
	
	@Autowired
	public OwnerController(OwnerRepository ownerRepository, OwnerModelAssembler assembler) {
		this.ownerRepository = ownerRepository;
		this.assembler = assembler;
	}
	
	// Get All Owners
	@GetMapping
	public CollectionModel<EntityModel<Owner>> getAllOwners(){
		List<EntityModel<Owner>> owners = ownerRepository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(owners,
				linkTo(methodOn(OwnerController.class).getAllOwners()).withRel("owners"));
	}
	
	// Get a single owner by ID
	@GetMapping("/{ownerId}")
	public EntityModel<Owner> getOwner(@PathVariable Long ownerId){
		Owner owner = ownerRepository.findById(ownerId)
					.orElseThrow(() -> new OwnerNotFoundException(ownerId));
		return assembler.toModel(owner);
	}
	
	// Find Owners by name
	@GetMapping("/search/name/{firstName}")
	public CollectionModel<EntityModel<Owner>> findByFirstName(@PathVariable String firstName){
		List<EntityModel<Owner>> owners = ownerRepository.findByFirstName(firstName).stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(owners,
					linkTo(methodOn(OwnerController.class).getAllOwners()).withRel("owners"));
	}
	
	// Save an Owner
	@PostMapping
	public EntityModel<Owner> newCar(@RequestBody Owner newOwner){
		Owner owner = ownerRepository.save(newOwner);
		return assembler.toModel(owner);
	}
	
	// TODO: finish HATEOAS for following methods
	// Update an Owner
	@PutMapping("/{ownerId}")
	public Owner updateOwnerInfo(@RequestBody Owner newOwner, @PathVariable Long ownerId) {
		return ownerRepository.findById(ownerId)
				.map(owner -> {
					owner.setFirstName(newOwner.getFirstName());
					owner.setLastName(newOwner.getLastName());
					return ownerRepository.save(owner);
				})
				.orElseGet(() -> {
					newOwner.setOwnerId(ownerId);
					return ownerRepository.save(newOwner);
				});
	}
	
	// Delete Owner
	@DeleteMapping("/{id}")
	public void deleteOwner(@PathVariable Long id) {
		ownerRepository.deleteById(id);
	}
		
}
