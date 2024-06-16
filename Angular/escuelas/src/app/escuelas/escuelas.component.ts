import { Component, OnInit } from '@angular/core';
import { Escuela } from '../escuela';
import { EscuelaService } from '../escuela.service';
import { MensajeService } from '../mensaje.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-escuelas',
  templateUrl: './escuelas.component.html',
  styleUrl: './escuelas.component.css',
})
export class EscuelasComponent implements OnInit{
  escuelas: Escuela[] = [];
  constructor(private escuelaService: EscuelaService, private mensajeService: MensajeService, private router: Router) {}
  //escuelaSeleccionada?: Escuela;

  // onSelect(escuela: Escuela): void {
  //   this.escuelaSeleccionada = escuela;
  //   this.mensajeService.add(`EscuelasComponent: Escuela seleccionada id=${escuela.id}`)
  // }

  ngOnInit(): void {
    this.getEscuelas();
  }

  getEscuelas(): void {
    this.escuelaService.getEscuelas()
      .subscribe(escuelas => this.escuelas = escuelas)
  }

  add(nombre: string): void {
    nombre = nombre.trim();
    if (!nombre) { return; }
    this.escuelaService.addEscuela({ nombre } as Escuela).subscribe(newEscuela => {
      this.escuelas.push(newEscuela);
    });
  }

  goToAddEscuela(): void {
    this.router.navigate(['/agregar-escuela']);
  }

  delete(escuela: Escuela): void {
    this.escuelas = this.escuelas.filter(h => h !== escuela);
    this.escuelaService.deleteEscuela(escuela.id).subscribe();
  }
}
