import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResolucionNotas } from '../models/resoluciones/ResolucionNotas';
import { NotasDebitoCredito } from '../models/resoluciones/notasDebitoCredito';
import { Resultado } from '../models/resultado';

@Injectable({
 providedIn: 'root'
})
export class ResolucionesNotasService {

 private urlBase = environment.urlBaseServicio;

 constructor(
  private http: HttpClient
 ) { }

 public obtenerResolucionNotas(sucursal, compania, producto): Observable<ResolucionNotas> {
  const url = `${this.urlBase}/resolucion/notas/consulta`;
  const httpOptions = new HttpHeaders()
   .append("X-SUCURSAL", sucursal.toString())
   .append("X-COMPANIA", compania.toString())
   .append("X-PRODUCTO", producto);
  return this.http.get(url, { headers: httpOptions }).pipe(
   map((response: any) => response.resultado)
  );
 }

 public editarResolucionNotas(nota: NotasDebitoCredito): Observable<Resultado> {
  const url = `${this.urlBase}/resolucion/notas/editar`;
  return this.http.post(url, nota).pipe(
   map((response: any) => response.resultado)
  );
 }

 public crearResolucionNotas(nota: NotasDebitoCredito): Observable<Resultado> {
  const url = `${this.urlBase}/resolucion/notas/crear`;
  return this.http.post(url, nota).pipe(
   map((response: any) => response.resultado)
  );
 }

 public eliminarResolucionNotas(nota: NotasDebitoCredito): Observable<Resultado> {
  const url = `${this.urlBase}/resolucion/notas/eliminar`;
  return this.http.post(url, nota).pipe(
   map((response: any) => response.resultado)
  );
 }

}