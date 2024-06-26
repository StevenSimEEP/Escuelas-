package com.example.escuela;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EscuelaRepository extends CrudRepository<Escuela, Long>,
	PagingAndSortingRepository<Escuela, Long>{

}
