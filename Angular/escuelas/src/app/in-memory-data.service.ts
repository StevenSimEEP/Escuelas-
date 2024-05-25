import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Escuela } from './escuela';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService {
  createDb() {
    const escuelas = [
      { id: 12, nombre: 'Bedoya' },
      { id: 13, nombre: 'Luxemburgo' },
      { id: 14, nombre: 'Amigos felices' },
      { id: 15, nombre: 'Dillon' },
      { id: 16, nombre: 'Montalvo' },
      { id: 17, nombre: 'Central' },
      { id: 18, nombre: 'Oxford' },
      { id: 19, nombre: 'EEP' }
    ];
    return {escuelas};
  }

  constructor() { }

  genId(escuelas: Escuela[]): number {
    return escuelas.length > 0 ? Math.max(...escuelas.map(escuela => escuela.id)) +
    1 : 11;
  }
}
