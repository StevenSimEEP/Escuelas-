import { Component, inject } from '@angular/core';
import { UniversidadComponent } from '../universidad/universidad.component';
import { Universidad } from '../universidad';
import { CommonModule } from '@angular/common';
import { UniversidadService } from '../universidad.service';
import { RouterModule } from '@angular/router';
import { DeatilsComponent } from '../deatils/deatils.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    UniversidadComponent,
    DeatilsComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  listaUniversidades: Universidad[] = [];
  universidadService: UniversidadService = inject(UniversidadService);
  listaUniversidadesFiltrada: Universidad[] = [];

  constructor() {
    this.universidadService.getAllUniversidades().subscribe(
      universidades => {
        this.listaUniversidades = universidades;
        this.listaUniversidadesFiltrada = universidades;
      }
    );

    this.universidadService.getUniversidadActualizadaObservable().subscribe(
      updatedUniversidad => {
        if (updatedUniversidad) {
          const index = this.listaUniversidades.findIndex(u => u.id === updatedUniversidad.id);
          if (index !== -1) {
            this.listaUniversidades[index] = updatedUniversidad;
          }
        }
      }
    );
   }

  filtrar(text: string) {
    if (!text) {
      this.listaUniversidadesFiltrada = this.listaUniversidades;
    } else {
      this.listaUniversidadesFiltrada = this.listaUniversidades.filter(
        universidad => universidad?.nombre.toLowerCase().includes(text.toLowerCase())
      );
    }
  }

  eliminarUniversidad(id: number) {
    this.listaUniversidadesFiltrada = this.listaUniversidadesFiltrada.filter(u => u.id !== id);
  }
}
