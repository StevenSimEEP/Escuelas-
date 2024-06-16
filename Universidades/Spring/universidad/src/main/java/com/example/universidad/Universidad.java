package com.example.universidad;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Universidad {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private String ubicacion;
	private String estado;
	private String photo;
	private String disponibilidad;

	public Universidad() {
		
	}
	
	@Override
	public String toString() {
		return "Universidad [id=" + id + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", estado=" + estado
				+ ", photo=" + photo + ", disponibilidad=" + disponibilidad + "]";
	}

	public Universidad(Long id, String nombre, String ubicacion, String estado,
				String photo, String disponibilidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.photo = photo;
		this.disponibilidad = disponibilidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(disponibilidad, estado, id, nombre, photo, ubicacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Universidad other = (Universidad) obj;
		return Objects.equals(disponibilidad, other.disponibilidad) && Objects.equals(estado, other.estado)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(photo, other.photo) && Objects.equals(ubicacion, other.ubicacion);
	}

	
	
	
}
