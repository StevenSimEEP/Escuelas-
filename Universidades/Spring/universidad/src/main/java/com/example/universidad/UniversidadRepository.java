package com.example.universidad;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UniversidadRepository extends CrudRepository<Universidad, Long>,
	PagingAndSortingRepository<Universidad, Long>{
	
}
