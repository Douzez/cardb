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
	public ResponseEntity<?> newCar(@RequestBody Owner newOwner){
		// EntityModel<Owner>
		EntityModel<Owner> entityModel = assembler.toModel(ownerRepository.save(newOwner));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
		
	}
	
	// TODO: finish HATEOAS for following methods
	// Update an Owner
	@PutMapping("/{ownerId}")
	public ResponseEntity<?> updateOwnerInfo(@RequestBody Owner newOwner, @PathVariable Long ownerId) {
		Owner updatedOwner = ownerRepository.findById(ownerId)
				.map(owner -> {
					owner.setFirstName(newOwner.getFirstName());
					owner.setLastName(newOwner.getLastName());
					return ownerRepository.save(owner);
				})
				.orElseGet(() -> {
					newOwner.setOwnerId(ownerId);
					return ownerRepository.save(newOwner);
				});
		
		EntityModel<Owner> entityModel = assembler.toModel(updatedOwner);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	// Delete Owner
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
		ownerRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
		
}
