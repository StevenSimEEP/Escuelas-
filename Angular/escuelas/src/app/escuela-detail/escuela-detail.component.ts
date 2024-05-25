import { Component, OnInit } from '@angular/core';
import { Escuela } from '../escuela';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { EscuelaService } from '../escuela.service';

@Component({
  selector: 'app-escuela-detail',
  templateUrl: './escuela-detail.component.html',
  styleUrl: './escuela-detail.component.css'
})
export class EscuelaDetailComponent implements OnInit{
  escuela: Escuela | undefined;

  constructor(
    private route: ActivatedRoute,
    private escuelaService: EscuelaService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getEscuela();
  }

  getEscuela(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.escuelaService.getEscuela(id)
      .subscribe(escuela => this.escuela = escuela);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.escuela) {
      this.escuelaService.updateEscuela(this.escuela)
        .subscribe(() => this.goBack());
    }
  }

  delete(): void {
    if (this.escuela && this.escuela.id) {
      this.escuelaService.deleteEscuela(this.escuela.id)
        .subscribe(() => {
          this.router.navigate(['/escuelas']);
        });
    }
  }
}
