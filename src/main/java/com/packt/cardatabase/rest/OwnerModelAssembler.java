package com.packt.cardatabase.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.packt.cardatabase.domain.Owner;

@Component
public class OwnerModelAssembler implements RepresentationModelAssembler<Owner, EntityModel<Owner>> {

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.server.RepresentationModelAssembler#toModel(java.lang.Object)
	 */
	@Override
	public EntityModel<Owner> toModel(Owner owner) {
		
		return EntityModel.of(owner,
				linkTo(methodOn(OwnerController.class).getOwner(owner.getOwnerId())).withSelfRel(),
				linkTo(methodOn(OwnerController.class).getAllOwners()).withRel("owners"));
	}
	
}
