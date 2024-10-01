import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { parametrosGenerales } from '../models/ParametrosGenerales';
import { parametrosProveedor } from '../models/parametros';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
// tslint:disable-next-line: class-name
export class parametrosService {
  public urlBase: string;

  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio; // 'http://172.17.5.51:8081';
  }

  AgregarDatosGenerales(parametrosGenerales: parametrosGenerales) {
    const httpOptions = new HttpHeaders().append(
      'Content-Type',
      'application/json; charset=UTF-8'
    );
    const url = `${this.urlBase}/parametros/agregarG`;
    return this.http.post(url, parametrosGenerales, { headers: httpOptions });
  }
  obtenerDatosGenerales() {
    const url = `${this.urlBase}/parametros/BuscarG`;
    return this.http.get(url);
  }

  AgregarDatosproveedor(parametrosProv: parametrosProveedor) {
    const httpOptions = new HttpHeaders().append(
      'Content-Type',
      'application/json; charset=UTF-8'
      );
      const url = `${this.urlBase}/parametros/agregarP`;
      return this.http.post(url, parametrosProv, { headers: httpOptions });

    }
    obtenerDatosProveedor() {
      const url = `${this.urlBase}/parametros/BuscarP`;
      return this.http.get(url);
    }
    listaPaises() {
      const url = `${this.urlBase}/adquirientes/Paises`;
      return this.http.get(url);
    }

    listaMonedas() {
      const url = `${this.urlBase}/adquirientes/Monedas`;
      return this.http.get(url);
    }

    listaAmbiente() {
      const url = `${this.urlBase}/adquirientes/AmbientesDestino`;
      return this.http.get(url);
    }

    listaParametros() {
      const url = `${this.urlBase}/parametros/consultar/todos`;
      return this.http.get(url);
    }

    consultarParametroId(dni: number) {
      const url = `${this.urlBase}/parametros/consultar?dni=${dni}`;
      return this.http.get(url);
    }

    actualizarParametro(body: any) {
      const url = `${this.urlBase}/parametros/editar`;
      return this.http.put(url, body);
    }

}
