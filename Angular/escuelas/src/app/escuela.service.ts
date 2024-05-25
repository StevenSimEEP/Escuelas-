import { Injectable } from '@angular/core';
import { Escuela } from './escuela';
import { Observable, of } from 'rxjs';
import { MensajeService } from './mensaje.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EscuelaService {

  private escuelasUrl = 'http://localhost:8080/escuelas';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private http: HttpClient,
    private mensajeService: MensajeService) { }

  getEscuelas(): Observable<Escuela[]> {
    return this.http.get<Escuela[]>(this.escuelasUrl)
      .pipe(
        tap(_ => this.log('fetched escuelas')),
        catchError(this.handleError<Escuela[]>('getEscuelas', []))
      );
  }

  getEscuela(id: number): Observable<Escuela> {
    const url = `${this.escuelasUrl}/${id}`;
    return this.http.get<Escuela>(url).pipe(
      tap(_ => this.log(`fetched escuela id=${id}`))
    )
  }

  searchEscuelas(term: string): Observable<Escuela[]> {
    if (!term.trim()) {
       // if not search term, return empty hero array.
      return of([]);
     }
     return this.http.get<Escuela[]>(`${this.escuelasUrl}/?name=${term}`).pipe(
       tap(x => x.length ?
          this.log(`found escuelas matching "${term}"`) :
         this.log(`no escuelas matching "${term}"`)),
       catchError(this.handleError<Escuela[]>('searchHeroes', []))
     );
   }

  updateEscuela(escuela: Escuela): Observable<any> {
    return this.http.put(this.escuelasUrl, escuela, this.httpOptions).pipe(
      tap(_ => this.log(`updated escuela id=${escuela.id}`)),
      catchError(this.handleError<any>('updateEscuela'))
    );
  }

  addEscuela(escuela: Escuela): Observable<Escuela> {
    return this.http.post<Escuela>(this.escuelasUrl, escuela, this.httpOptions).pipe(
      tap((newEscuela: Escuela) => {
        this.log(`added escuela w/ id=${newEscuela.id}`);
      }),
      catchError(this.handleError<Escuela>('addEscuela'))
    );
}

  deleteEscuela(id: number): Observable<Escuela> {
    const url = `${this.escuelasUrl}/${id}`;
    return this.http.delete<Escuela>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted escuela id=${id}`)),
      catchError(this.handleError<Escuela>('deleteEscuela'))
    );
  }

  /* GET heroes whose name contains search term */

  private log(mensaje: string) {
    this.mensajeService.add(`EscuelaService: ${mensaje}`);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.mensaje}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
