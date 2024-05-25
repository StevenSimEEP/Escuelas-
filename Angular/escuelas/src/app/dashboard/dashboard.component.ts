import { Component, OnInit } from '@angular/core';
import { Escuela } from '../escuela';
import { EscuelaService } from '../escuela.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  escuelas: Escuela[] = [];

  constructor(private escuelaService: EscuelaService) { }

  ngOnInit(): void {
    this.getUniversidades();
  }

  getUniversidades(): void {
    this.escuelaService.getEscuelas()
      .subscribe(escuelas => this.escuelas = escuelas.slice(1, 5));
  }
}
