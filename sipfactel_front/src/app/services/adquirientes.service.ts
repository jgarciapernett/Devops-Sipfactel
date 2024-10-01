import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { url } from 'inspector';
import { Adquirientes } from './../models/adquirientes/TipoDocumento';
import { Adquiriente } from 'src/app/models/adquirientes/adquiriente';
import { datosA } from '../models/adquirientes/datosAdministrables';
import { Lista } from '../models/lista';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdquirientesService {
  public urlBase = '';

  constructor(
    private http: HttpClient
  ) {
    this.urlBase = environment.urlBaseServicio;
  }
  obtenerListaIdentificacion() {
    const httpOptions = new HttpHeaders().append(
      "Content-Type",
      "application/json; charset=UTF-8"
    );
    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/adquirientes/TipoIdentificacion`;
    return this.http.get(url, { headers: httpOptions });
  }
  listaObligacionesFiscales(){
    const url = `${this.urlBase}/adquirientes/OblFiscales`;
    return this.http.get(url);
  }
  listaPer(){
    const url = `${this.urlBase}/adquirientes/TipoPersona`;
    return this.http.get(url);
  }
  documento(){
    const url = `${this.urlBase}/adquirientes/TipoIdentificacion`;
    return this.http.get(url);


  }

  public obtenerListaPaises (): Observable<Lista[]> {
    const url = `${this.urlBase}/adquirientes/Paises`;
    return this.http.get(url).pipe(map((response: any) => {
      return response.resultado;
    }));
  }

  obtenerListaCiudades() {

    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/adquirientes/Ciudades`;
    return this.http.get(url);
  }
  obtenerOpcionListaPorId(id: number) {
    const url = `${this.urlBase}/adquirientes/buscarID?id=${id}`;
    return this.http.get(url);
  }

  obtenerOpcionListaPorIdold(id: number) {
    const url = `${this.urlBase}/adquirientes/buscarIDold?id=${id}`;
    return this.http.get(url);
  }

  obtenerListaDepartamentos() {
    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/adquirientes/Departamentos`;
    return this.http.get(url);
  }
  obtenerFacturaElectronica() {
    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/adquirientes/EnvioFE`;
    return this.http.get(url);
  }

  obtenerDatosAdministrables() {
    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/datosAdministrables/BuscarTodos`;
    return this.http.get(url);
  }

  obtenerTributo() {
    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/adquirientes/TipoTributos`;
    return this.http.get(url);
  }

  obtenerRegimenFiscal() {
    const url = `${this.urlBase}/adquirientes/RegimenFiscal`;
    return this.http.get(url);

  }

  obtenerCodigoPostal() {
    const url = `${this.urlBase}/sucursales/codigoPostal`;
    return this.http.get(url);

  }
  filtroAdquiriente(tipo: any, nombre: string, razon: string, numeroDoc: number) {

    if (nombre === 'tipoDoc') {
      const url = `${this.urlBase}/adquirientes/ConsAdquirientes?tipoidentificacion=${tipo}`;
      return this.http.get(url);
    } else if (nombre === 'numeroDoc') {
      const url = `${this.urlBase}/adquirientes/ConsAdquirientes?numidentificacion=${numeroDoc}`;
      return this.http.get(url);
    } else if (nombre === 'razonSocial') {
      const url = `${this.urlBase}/adquirientes/ConsAdquirientes?razonsocial=${razon}`;
      return this.http.get(url);
    } else if (nombre === 'Tdoc&nDoc') {
      const url = `${this.urlBase}/adquirientes/ConsAdquirientes?numidentificacion=${numeroDoc}&tipoidentificacion=${tipo}`;
      return this.http.get(url);
    }else if (nombre === 'Tdoc&razon') {
      const url = `${this.urlBase}/adquirientes/ConsAdquirientes?razonsocial=${razon}&tipoidentificacion=${tipo}`;
      return this.http.get(url);
    }

    else {
      const url = `${this.urlBase}/adquirientes/ConsAdquirientes`;
      return this.http.get(url);

    }

  }




  editarAdquirientes(adquiriente: any, dniDocumento: number, tipo: string) {
    const httpOptions = new HttpHeaders()
    .append('Content-Type', 'application/json; charset=UTF-8')
    .append("documento", String(dniDocumento))
    .append("tipo", tipo);
    const url = `${this.urlBase}/adquirientes/editar`;
    return this.http.put(url, adquiriente, { headers: httpOptions });
  }

  editarDatos(adquiriente: datosA, id: number) {
    const httpOptions = new HttpHeaders().append('Content-Type', 'application/json; charset=UTF-8');
    const url = `${this.urlBase}/datosAdministrables/editar?id=${id}`;
    return this.http.put(url, adquiriente, { headers: httpOptions });
  }
}
