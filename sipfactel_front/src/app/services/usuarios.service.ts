import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable } from 'rxjs/Observable';

//#region modelo
import { Usuarios } from '../models/usuarios';
import { Params } from '@angular/router';
//#endregion

@Injectable({
  providedIn: "root"
})
export class UsuariosService {
  public urlBase = "";
  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio;
  }
  
  obtenerUsuariosRegistrados(usuaUsuario: string){
    
    const url = `${this.urlBase}/Usuarios/consultar?nombre=${usuaUsuario}`;
    return this.http.get(url);
  }
  
  agregarUsuario(usuarioObj: Usuarios){
    const httpOptions = new HttpHeaders().append(
      'Content-Type',
      'application/json; charset=UTF-8'
    );
    const url = `${this.urlBase}/Usuarios/agregar`;
    return this.http.post(url, usuarioObj, { headers: httpOptions });
  }

  obtenerRegistroPorId(id: number){
    const url = `${this.urlBase}/Usuarios/consultarPorId?id=${id}`;
    return this.http.get(url);
  }

  DescargarExcel(): Observable<Blob>{
    const url = `${this.urlBase}/Usuarios/GeneraExcel`;
    return this.http.get(url, {responseType: 'blob' });
  }

  buscarTodosLosUsuarios(){
    
    const url = `${this.urlBase}/Usuarios/BuscarTodos`;
    return this.http.get(url);
  }

  EditarUsuario(usuarioObj: Usuarios){
    const httpOptions = new HttpHeaders().append('Content-Type', 'application/json; charset=UTF-8');
    const url = `${this.urlBase}/Usuarios/actualizar?id=${usuarioObj.usuaUsua}`;
    return this.http.put(url, usuarioObj, { headers: httpOptions });
  }

  obtenerRol() {
    const url = `${this.urlBase}/Usuarios/consultarRoles`;
    return this.http.get(url);
  }
}
