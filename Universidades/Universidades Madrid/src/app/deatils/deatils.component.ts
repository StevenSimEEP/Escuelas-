import { Component, inject, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UniversidadService } from '../universidad.service';
import { Universidad } from '../universidad';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-deatils',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './deatils.component.html',
  styleUrl: './deatils.component.css'
})
export class DeatilsComponent {
  route: ActivatedRoute = inject(ActivatedRoute);
  universidadService = inject(UniversidadService);
  universidad: Universidad | undefined;
  mostrarFormulario: boolean = false;
  textoBoton: string = 'Modificar universidad';
applyForm = new FormGroup({
  nombre: new FormControl(''),
  ubicacion: new FormControl(''),
  estado: new FormControl(''),
  disponibilidad: new FormControl(''),
  photo: new FormControl('')
});

  @Output() universidadActualizada = new EventEmitter<Universidad>();

  constructor(private router: Router) {
    const universidadId = Number(this.route.snapshot.params['id']);
    this.universidadService.getUniversidadById(universidadId).subscribe(
      universidad => {
        this.universidad = universidad;
        this.applyForm.patchValue({
          nombre: universidad.nombre,
          ubicacion: universidad.ubicacion,
          estado: universidad.estado,
          disponibilidad: universidad.disponibilidad,
          photo: universidad.photo
        });
      }
    );

    this.universidadService.getUniversidadActualizadaObservable().subscribe(
      updatedUniversidad => {
        if (updatedUniversidad && this.universidad && updatedUniversidad.id === this.universidad.id) {
          this.universidad = updatedUniversidad;
        }
      }
    );
  }

  eliminarUniversidad(): void {
    if (this.universidad) {
      this.universidadService.eliminarUniversidad(this.universidad.id).subscribe(
        () => {
          console.log('Universidad eliminada exitosamente');
          this.router.navigate(['/']);
        },
        error => {
          console.error('Error al eliminar la universidad:', error);
        }
      );
    }
  }
  submitApplication() {
    if (this.universidad) {
      const updatedUniversidad: Universidad = {
        nombre: this.applyForm.value.nombre || '',
        ubicacion: this.applyForm.value.ubicacion || '',
        estado: this.applyForm.value.estado || '',
        disponibilidad: this.applyForm.value.disponibilidad || '',
        id: this.universidad.id || 0,
        photo: this.applyForm.value.photo || this.universidad.photo
      };

      this.universidadService.updateUniversidad(updatedUniversidad).subscribe(
        updated => {
          console.log("xd", updated)
          this.universidad = updated;
          this.universidadActualizada.emit(updated);
        },
        error => {
          console.error('Error actualizando universidad:', error);
        }
      );
    }
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    this.textoBoton = this.mostrarFormulario ? 'Ocultar modificador' : 'Modificar universidad';
  }
}
