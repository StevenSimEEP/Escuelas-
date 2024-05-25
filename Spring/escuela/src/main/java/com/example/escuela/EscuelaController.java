package com.example.escuela;

import java.net.URI;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/escuelas")
public class EscuelaController {
	private final EscuelaRepository escuelaRepository;

	public EscuelaController(EscuelaRepository escuelaRepository) {
		this.escuelaRepository = escuelaRepository;
	}

	@GetMapping("/{requestedId}")
	private ResponseEntity<Escuela> findById(@PathVariable Long requestedId) {
		Optional<Escuela> escuelaOptional = escuelaRepository.findById(requestedId);
		if (escuelaOptional.isPresent()) {
			return ResponseEntity.ok(escuelaOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping()
	private ResponseEntity<Iterable<Escuela>> findAll(Pageable pageable) {
		Page<Escuela> page = escuelaRepository.findAll(
	            PageRequest.of(
	                    pageable.getPageNumber(),
	                    pageable.getPageSize(),
	                    pageable.getSortOr(Sort.by(Sort.Direction.ASC, "nombre"))
	    ));
	    return ResponseEntity.ok(page.getContent());
	}

	@PostMapping
	private ResponseEntity<Escuela> crearEscuela(@RequestBody Escuela newEscuelaRequest, UriComponentsBuilder ucb) {
	    Escuela savedEscuela = escuelaRepository.save(newEscuelaRequest);
	    URI locationOfNewEscuela = ucb.path("escuelas/{id}").buildAndExpand(savedEscuela.getId()).toUri();
	    return ResponseEntity.created(locationOfNewEscuela).body(savedEscuela);
	}
	
	@PutMapping("/{requestedId}")
	private ResponseEntity<Void> putEscuela(@PathVariable Long requestedId, @RequestBody Escuela escuelaActualizada) {
	    Optional<Escuela> optional = escuelaRepository.findById(requestedId);
	    if (optional.isPresent()) {
	    	Escuela escuela = optional.get();
		    Escuela updatedEscuela = new Escuela(
		    		escuela.getId(), 
		    		escuelaActualizada.getNombre());
		    escuelaRepository.save(updatedEscuela);
		    return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<Void> deleteUniversidad(@PathVariable Long id) {
		escuelaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
