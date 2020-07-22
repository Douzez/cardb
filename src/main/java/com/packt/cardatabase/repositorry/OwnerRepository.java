package com.packt.cardatabase.repositorry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.packt.cardatabase.domain.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	// Fetch Owners by firstName
	List<Owner> findByFirstNameOrderByLastNameAsc(String firstName);
	
	// Fetch Owner by lastName
	List<Owner> findByLastNameOrderByFirstNameAsc(String lastName);
	
	// Fetch owner by ending pattern
	@Query("select o from Owner o where o.firstName like %?1%")
	List<Owner> findByFirstName(String firstName);
}
