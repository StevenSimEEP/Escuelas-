import { Component } from '@angular/core';
import { Escuela } from '../escuela';
import { EscuelaService } from '../escuela.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agregar-escuela',
  templateUrl: './agregar-escuela.component.html',
  styleUrl: './agregar-escuela.component.css'
})
export class AgregarEscuelaComponent {
  escuela: Escuela = { nombre: '' } as Escuela;

  constructor(private escuelaService: EscuelaService, private router: Router) {}
  addEscuela(): void {
    this.escuelaService.addEscuela(this.escuela).subscribe(() => {
      this.router.navigate(['/escuelas']);
    });
  }

  goBack(): void {
    this.router.navigate(['/escuelas']);
  }
}
