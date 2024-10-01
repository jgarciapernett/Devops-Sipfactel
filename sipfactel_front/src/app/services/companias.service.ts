import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Usuario } from "../models/usuario";
import { Compania } from "../models/compania/compania";

@Injectable({
  providedIn: "root",
})
export class CompaniasService {
  public urlBase = "";

  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio;
  }
  obtenerTributo() {
    // tslint:disable-next-line: no-shadowed-variable
    const url = `${this.urlBase}/adquirientes/TipoTributos`;
    return this.http.get(url);
  }
  obtenerOpcionesLista() {
    const url = `${this.urlBase}/companias/opciones`;
    return this.http.get(url);
  }

  obtenerOpcionListaPorId(id: number) {
    const url = `${this.urlBase}/companias/buscarID?id=${id}`;
    return this.http.get(url);
  }

  editarCompania(Compania: Compania) {
    const httpOptions = new HttpHeaders().append(
      "Content-Type",
      "application/json; charset=UTF-8"
    );
    const url = `${this.urlBase}/companias/editar?id=${Compania.comp}`;
    return this.http.put(url, Compania, { headers: httpOptions });
  }

  insertarCompania(compania: Compania) {
    console.log('agrego',JSON.stringify(compania));
    
    const httpOptions = new HttpHeaders().append(
      "Content-Type",
      "application/json; charset=UTF-8"
    );
    const url = `${this.urlBase}/companias/crear`;
    return this.http.post(url, compania, { headers: httpOptions });
  }

  listaPaises() {
    const url = `${this.urlBase}/adquirientes/Paises`;
    return this.http.get(url);
  }

  listaMetodoPago(){
    const url = `${this.urlBase}/adquirientes/MediosPago`;
    return this.http.get(url);
  }

  listaUnidadMedida(){
    const url = `${this.urlBase}/adquirientes/UnidadesMedida`;
    return this.http.get(url);
  }

  listaRegimenesFiscales(){
    const url = `${this.urlBase}/adquirientes/RegimenFiscal`;
    return this.http.get(url);
  }

  listaFormaPago(){
    const url = `${this.urlBase}/adquirientes/FormaDePago`;
    return this.http.get(url);
  }

  listaProductos(){
    const url = `${this.urlBase}/adquirientes/Productos`;
    return this.http.get(url);
  }

  listaTipoTributo(){
    const url = `${this.urlBase}/adquirientes/TipoTributos`;
    return this.http.get(url);
  }

  listaPorcentajeAtributo(){
    const url = `${this.urlBase}/adquirientes/Porcentajes`;
    return this.http.get(url);
  }

  listaFacturaElectronica(){
    const url = `${this.urlBase}/adquirientes/TipoOperacion`;
    return this.http.get(url);
  }

  listaTipoDocumento(){
    const url = `${this.urlBase}/adquirientes/TipoIdentificacion`;
    return this.http.get(url);
  }

  listaTipoPersona(){
    const url = `${this.urlBase}/adquirientes/TipoPersona`;
    return this.http.get(url);
  }

  listaObligacionesFiscales(){  
    const url = `${this.urlBase}/adquirientes/OblFiscales`;
    return this.http.get(url);
  }
}
