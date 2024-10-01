import {
  Component,
  OnInit,
  SimpleChanges
} from "@angular/core";
import { FormControl } from "@angular/forms";
import { Subject } from "rxjs/internal/Subject";

//#region rutas
import { Router } from "@angular/router";
//#endregion

// #region servicios
import { UtilidadesService } from "../../../services/utilidades.service";
import { SucursalService } from "../../../services/sucursal.service";
import { PaginacionService } from "../../../services/paginacion.service";
// #endregion servicios

//#region modelos
import { SucuTabla } from "src/app/models/sucursales/tablaSucu";
import { sucursales } from "../../../models/sucursales/sucursales";
//#endregion

//#region bibliotecas
import Swal from "sweetalert2";
import * as _ from "lodash";
import { HttpErrorResponse } from "@angular/common/http";
//#endregion

@Component({
  // tslint:disable-next-line: component-selector
  selector: "sucursales-home",
  templateUrl: "./home.component.html",
  providers: [SucursalService, PaginacionService, UtilidadesService]
})
export class HomeComponent implements OnInit {
  public filtroTabla = new FormControl(" ");
  public opcionesFiltro$: Subject<sucursales[]> = new Subject<sucursales[]>();
  public opcion: SucuTabla[] = [];
  public resultado : string
  public id : number
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;



  constructor(
    private SucuService: SucursalService,
    private router: Router,


  ) {
    this.id = 0
    this.ObtenerPermisos('sucursales');

  }

  ngOnInit(){
    this.obtenerSucursalLista()
  }
  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que tenga
    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    menu.forEach(item => { // Mapeo de los modulos y sus respectivos permisos
      item.menuHijos.map(alias => {
        if( alias.menuAlias === menuAlias){
          this.esCrear = alias.crear;
          this.esConsultar = alias.consultar;
          this.esEditar = alias.modificar;
        }
       else if (item.menuAlias === menuAlias ) {

          this.esCrear = item.crear;
          this.esConsultar = item.consultar;
          this.esEditar = item.modificar;
        }
      })


    });



  }
  obtenerSucursalLista() {
    this.SucuService.obtenerCompaniasSeleccionables().subscribe((res: any) => {
      this.opcion = res.resultado;
      this.resultado = JSON.stringify(res);


    });
  }



  obtenerFormularioEditar(id:number){
    this. id = id
    this.router.navigate(['/Sucursales/editar',this.id])

  }
  obtenerFormulario(){
    this.router.navigate(['/Sucursales/agregar'])

  }
}
