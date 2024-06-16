import { Injectable } from '@angular/core';
import { Universidad } from './universidad';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UniversidadService {
  url = 'http://localhost:8080/universidades';

  private universidadActualizadaSubject = new Subject<Universidad>();

  constructor(private http: HttpClient) {}

  updateUniversidad(updatedUniversidad: Universidad): Observable<Universidad> {
    return this.http.put<Universidad>(`${this.url}/${updatedUniversidad.id}`, updatedUniversidad);
  }

  getAllUniversidades() {
    return this.http.get<Universidad[]>(this.url);
  }

  getUniversidadById(id: number) {
    return this.http.get<Universidad>(`${this.url}/${id}`);
  }

  agregarUniversidad(universidadDatos: Omit<Universidad, 'id'>): Observable<Universidad> {
    return this.http.post<any>(this.url, universidadDatos);
  }
  getUniversidadActualizadaObservable(): Observable<Universidad> {
    return this.universidadActualizadaSubject.asObservable();
  }

  emitirUniversidadActualizada(universidad: Universidad | undefined) {
    if(universidad !== undefined) {
      this.universidadActualizadaSubject.next(universidad);
    }
  }
  eliminarUniversidad(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }
}


