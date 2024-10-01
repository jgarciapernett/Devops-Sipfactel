import { Injectable } from '@angular/core';
import { environment } from '../../../src/environments/environment';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { NgbDate } from '@ng-bootstrap/ng-bootstrap';


@Injectable({
  providedIn: 'root'
})
export class AuditoriaService {
  public urlBase = '';

  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio;
  }

  obtenerPorFechaYUsuario(fechaFin: string, fechaIni: string, usuario: string) {

    const url = `${this.urlBase}/auditoria/consultar?fechaFin=${fechaFin}&fechaIni=${fechaIni}&usuario=${usuario}`;
    return this.http.get(url);
  }

  descargarExcelAuditoria(fechaFin: string, fechaIni: string, usuario: string): Observable<Blob> {
    const url = `${this.urlBase}/auditoria/Generar excel?fechaFin=${fechaFin}&fechaIni=${fechaIni}&usuario=${usuario}`;
    return this.http.get(url, {responseType: 'blob' } );
  }
}
