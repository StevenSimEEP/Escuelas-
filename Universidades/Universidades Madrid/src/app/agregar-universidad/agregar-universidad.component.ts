import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UniversidadService } from '../universidad.service';

@Component({
  selector: 'app-agregar-universidad',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './agregar-universidad.component.html',
  styleUrl: './agregar-universidad.component.css'
})
export class AgregarUniversidadComponent {
  FormUniversidad = new FormGroup({
    nombre: new FormControl('', Validators.required),
    ubicacion: new FormControl('', Validators.required),
    estado: new FormControl('', Validators.required),
    photo: new FormControl(''),
    disponibilidad: new FormControl('', Validators.required)
  });

  constructor(private universidadService:UniversidadService) {}

  agregar() {
    const { nombre, ubicacion, estado, photo, disponibilidad } = this.FormUniversidad.value;

    const universidadDatos = {
      nombre: nombre?.trim() ?? '',
      ubicacion: ubicacion?.trim() ?? '',
      estado: estado?.trim() ?? '',
      photo: photo?.trim() ?? '',
      disponibilidad: disponibilidad?.trim() ?? ''
    };

    this.universidadService.agregarUniversidad(universidadDatos).subscribe({
      next: nuevaUniversidad => {
        console.log('Nueva universidad agregada', nuevaUniversidad);
        alert('Universidad agregada exitosamente');
      },
      error: error => {
        console.log('Error al agregar', error);
        alert('No se ha podido agregar');
      }
    })
  }
}
