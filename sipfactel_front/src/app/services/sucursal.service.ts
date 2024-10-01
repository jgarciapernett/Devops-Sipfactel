import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { SucuTabla } from 'src/app/models/sucursales/tablaSucu';
import { sucursales } from '../models/sucursales/sucursales';

@Injectable({
  providedIn: 'root'
})
export class SucursalService {

  url = 'http://172.17.5.51:8081';
  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio; 
  }
  public urlBase: string;
  obtenerLista() {
    throw new Error('Method not implemented.');
  }

    obtenerInfoResolucion(comp: number, sucu:number){

      const url = `${this.urlBase}/sucursales/consultar/resolucion?comp=${comp}&suc=${sucu}`
      return this.http.get(url);
    }
  insertarSucursales(sucuObj: sucursales) {
    const httpOptions = new HttpHeaders().append(
      'Content-Type',
      'application/json; charset=UTF-8'
      
      );
    const url = `${this.urlBase}/sucursales/crear`;
    return this.http.post(url, sucuObj, { headers: httpOptions });
  }

  obtenerCodigoPostal(){
    const url = `${this.urlBase}/sucursales/codigoPostal`;
    return this.http.get(url);
  }

  obtenerCompaniasSeleccionables() {
    const url = `${this.urlBase}/sucursales/consultarDatos`;
    return this.http.get(url);
  }

  obtenerCiudades(){
    const url = `${this.urlBase}/adquirientes/Ciudades`;
    return this.http.get(url);
  }
  obtenerDepartamento(){
    const url = `${this.urlBase}/adquirientes/Departamentos`;
    return this.http.get(url);

  }

  obtenerListaCompanias(){
    const url = `${this.urlBase}/companias/opciones`;
    return this.http.get(url);
  }

/**
 * Metodo que consume el servicio de tipos de poliza para resoluciones
 * @Return observable con la informaciond e tipos poliza
 */

  obtenerListaTiposPoliza(){
    const url = `${this.urlBase}/adquirientes/tiposPoliza`;
    return this.http.get(url);
  }

  obtenerInformacionDeSucursal(id: number){
    const url = `${this.urlBase}/sucursales/consultar?id=${id}`;
    return this.http.get(url);
  }

  editarLaSucursal(sucursales: sucursales) {
    console.log('editando dato',JSON.stringify(sucursales))
    const httpOptions = new HttpHeaders().append('Content-Type', 'application/json; charset=UTF-8');
    const url = `${this.urlBase}/sucursales/editar?id=${sucursales.sucu}`;
    return this.http.put(url, sucursales, { headers: httpOptions });
  }
}
