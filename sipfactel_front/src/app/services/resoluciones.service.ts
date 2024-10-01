import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResolucionFacturas } from '../models/resoluciones/ResolucionFacturas';
import { Resultado } from '../models/resultado';

@Injectable({
 providedIn: 'root'
})
export class ResolucionesService {

 private urlBase = environment.urlBaseServicio;

 constructor(
  private http: HttpClient
 ) { }

 public obtenerResolucionFacturas(sucursal, compania, producto): Observable<ResolucionFacturas[]> {
  const url = `${this.urlBase}/resolucion/facturas/consulta?compania=${compania}&sucursal=${sucursal}&producto=${producto}`;
  return this.http.get(url).pipe(
   map((response: any) => response.resultado)
  );
 }

 public obtenerRangosResolucionFacturas(sucursal, compania, producto): Observable<string[]> {
  const url = `${this.urlBase}/resolucion/facturas/Rango?compania=${compania}&sucursal=${sucursal}&producto=${producto}`;
  return this.http.get(url).pipe(
   map((response: any) => response.resultado)
  );
 }

 public eliminarResolucionFacturas(resolucion: ResolucionFacturas): Observable<Resultado> {
  const url = `${this.urlBase}/resolucion/facturas/eliminar`;
  return this.http.post(url, resolucion).pipe(
   map((response: any) => response.resultado)
  );
 }

 public crearResolucionFacturas(resolucion: ResolucionFacturas): Observable<Resultado> {
  const url = `${this.urlBase}/resolucion/facturas/crear`;
  return this.http.post(url, resolucion).pipe(
   map((response: any) => response.resultado)
  );
 }

 public editarResolucionFacturas(resolucion: ResolucionFacturas): Observable<Resultado> {
  const url = `${this.urlBase}/resolucion/facturas/update`;
  return this.http.post(url, resolucion).pipe(
   map((response: any) => response.resultado)
  );
 }

}