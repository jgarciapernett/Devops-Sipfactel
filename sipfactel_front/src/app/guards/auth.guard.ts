import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  CanActivateChild,
  UrlTree,
  Data
} from '@angular/router';
import { environment } from '../../environments/environment';

//#region modelos
import { Vista } from '../models/vista';
import { Role } from '../models/role';
import { Usuario } from '../models/usuario';
//#endregion modelos

//#region bibliotecas
import { Observable } from 'rxjs';
import Swal from 'sweetalert2';
import { ListaMenu } from '../models/lista-menu';
//#endregion bibliotecas


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  public listaMenu: ListaMenu[] = [];
  public sesion: any;
  public vista: string[];
  public isAutorizado: boolean;
  constructor(private router: Router) { }

  comprobarSiAutorizado(routeData: Data): any {
    this.vista = JSON.parse(localStorage.getItem('vistas'));
    this.isAutorizado = false;
    this.vista.forEach(itemVistas => {
      if (itemVistas === routeData.vista) {
        this.isAutorizado = true;
        return;
      }
    });
    return this.isAutorizado;
  }


  mostrarMensajeNoAutorizado() {
    Swal.fire({
      type: 'error',
      title: 'No autorizado',
      text: 'Usted no se encuentra autorizado para ingresar a este enlace',
      footer: ''
    });
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {

    this.comprobarSiAutorizado(childRoute.data);
    if (!this.isAutorizado) {
      this.mostrarMensajeNoAutorizado();
    }
    return this.isAutorizado;
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    const isAutorizado = this.comprobarSiAutorizado(route.firstChild.data);
    if (!isAutorizado) {
      this.mostrarMensajeNoAutorizado();
    }
    return isAutorizado;
  }
}
