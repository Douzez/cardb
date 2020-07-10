package com.packt.cardatabase.repositorry;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.packt.cardatabase.domain.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

	// Fetch Owners by firstName
	List<Owner> findByFirstNameOrderByLastNameAsc(String firstName);
	
	// Fetch Owner by lastName
	List<Owner> findByLastNameOrderByFirstNameAsc(String lastName);
	
	// Fetch owner by ending pattern
	@Query("select o from Owner o where o.firstName like %?1")
	List<Owner> findByFirstName(String firstName);
}
