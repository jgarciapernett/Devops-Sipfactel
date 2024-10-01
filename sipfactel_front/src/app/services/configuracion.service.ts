import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { opcion } from "../models/opcion";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root"
})
export class ConfiguracionService {
  public urlBase: string;

  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio; //'http://172.17.5.51:8081';
  }

  obtenerListaDeOpciones() {
    const url = `${this.urlBase}/configuracion/opciones`;
    return this.http.get(url);
  }

  obtenerOpcionElegida(id: number) {
    const url = `${this.urlBase}/configuracion/lista?id=${id}`;
    return this.http.get(url);
  }

  insertarConfiguracion(configuracion: opcion) {
    const httpOptions = new HttpHeaders().append(
      "Content-Type",
      "application/json; charset=UTF-8"
    );
    const url = `${this.urlBase}/configuracion/crear`;
    return this.http.post(url, configuracion, { headers: httpOptions });
  }

  obtenerOpcionPorId(id: number) {
   
    
    const url = `${this.urlBase}/configuracion/buscar?id=${id}`;
    return this.http.get(url);
  }

  editarLaConfiguracion(configuracion: opcion) {
    const httpOptions = new HttpHeaders().append(
      "Content-Type",
      "application/json; charset=UTF-8"
    );

    let objConfiguracion = new Object({
      codigo: configuracion.codigo,
      nombre: configuracion.nombre,
      padre : configuracion.padre,
      estado: configuracion.estado
    });
    const url = `${this.urlBase}/configuracion/actualizar?id=${configuracion.id}`;
    return this.http.put(url, objConfiguracion, { headers: httpOptions });
  }
}
