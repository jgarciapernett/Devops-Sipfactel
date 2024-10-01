import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EtlService {

  public urlBase ='';

  constructor(
    private http: HttpClient
    ) {
    this.urlBase = environment.urlBaseServicio;
  }

  obtenerEjecuciones() {
    const url = `${this.urlBase}/etl`;
    return this.http.get(url);
  }

  obtenerHoras() {
    const url = `${this.urlBase}/hora`;
    return this.http.get(url);
  }

  modificarHorario(horario: any) {
    const url = `${this.urlBase}/hora/editar`;
    return this.http.put(url, horario);
  }

}
