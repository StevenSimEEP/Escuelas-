import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Escuela } from '../escuela';

import { EscuelaService } from '../escuela.service';

@Component({
  selector: 'app-escuela-search',
  templateUrl: './escuela-search.component.html',
  styleUrl: './escuela-search.component.css'
})
export class EscuelaSearchComponent implements OnInit {
  escuelas$!: Observable<Escuela[]>;
  private searchTerms = new Subject<string>();

  constructor(private escuelaService: EscuelaService) {}

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.escuelas$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.escuelaService.searchEscuelas(term)),
    );
  }


}
