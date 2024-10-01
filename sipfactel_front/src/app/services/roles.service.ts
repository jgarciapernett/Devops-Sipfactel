import { Injectable } from '@angular/core';
import { environment } from '../../../src/environments/environment';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { roles } from '../models/roles';
import { agregar } from '../models/rolesAgregar';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  public urlBase = '';
  // public url = 'http://172.168.10.101:8285'

  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio;
  }
  categorias() {
     
    const url = `${this.urlBase}/adquirientes/Categorias`;
    return this.http.get(url);

  }
  obtenerRoles(roleNombre: string) {
    const url = `${this.urlBase}/roles/buscar?nombre=${roleNombre}`;
    return this.http.get(url);
  }
  obtenerRolesTodos() {
    const url = `${this.urlBase}/roles/consultarTodos`;
    return this.http.get(url);
  }
  listarMenus(){ const url = `${this.urlBase}/roles/menus`;
  return this.http.get(url);
}

 crearRoles(Roles:agregar){
   
  const httpOptions = new HttpHeaders().append(
    'Content-Type',
    'application/json; charset=UTF-8'
    
    );   
const url = `${this.urlBase}/roles/crear`;
 return this.http.post(url, Roles, {headers: httpOptions} );
}
obtenerOpcionPorId(roleRole: Number) {
  const url = `${this.urlBase}/roles/consultarById?id=${roleRole}`;
  return this.http.get(url);
}
EditarUsuario(parametrosObj: agregar){
 
  const httpOptions = new HttpHeaders().append('Content-Type', 'application/json; charset=UTF-8');
  const url = `${this.urlBase}/roles/actualizar?id=${parametrosObj.roleRole}`;
  console.log('data',JSON.stringify(parametrosObj))

  return this.http.put(url, parametrosObj, { headers: httpOptions });
}


}
