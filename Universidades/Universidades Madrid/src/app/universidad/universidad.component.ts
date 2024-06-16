import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Universidad } from '../universidad';
import { RouterModule } from '@angular/router';
import { UniversidadService } from '../universidad.service';

@Component({
  selector: 'app-universidad',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './universidad.component.html',
  styleUrl: './universidad.component.css'
})
export class UniversidadComponent {
  @Input() universidad!: Universidad;
  @Output() universidadEliminada: EventEmitter<number> = new EventEmitter<number>();


  constructor(private universidadService: UniversidadService) {}

  eliminarUniversidad(id: number) {
    this.universidadService.eliminarUniversidad(id).subscribe(
      () => {
        console.log('Universidad eliminada exitosamente');
        this.universidadEliminada.emit(id); // Emitir evento de eliminación
      },
      error => {
        console.error('Error al eliminar la universidad:', error);
        // Manejar el error adecuadamente, por ejemplo, mostrar un mensaje al usuario
      }
    );
  }
}
