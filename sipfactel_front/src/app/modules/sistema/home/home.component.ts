import { Component, OnInit } from '@angular/core';
import { Subject } from "rxjs/internal/Subject";
import { Router } from "@angular/router";
import { UtilidadesService } from "../../../services/utilidades.service";
import { PaginacionService } from "../../../services/paginacion.service";
import { parametrosService } from '../../../services/parametros.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [PaginacionService, UtilidadesService]
})
export class HomeComponent implements OnInit {

  public opcionesFiltro$: Subject<any[]> = new Subject<any[]>();
  public data: any[] = [];

  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;


  constructor(private service: parametrosService,
    private router: Router) {
      this.ObtenerPermisos('sistema');
     }

  ngOnInit() {
     this.consultar();
  }

  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que tenga
    const objsesion = JSON.parse(localStorage.getItem('sesion'));
    const menu = objsesion.listMenu;
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

  consultar(){
    this.service.listaParametros().subscribe({
      next: (resp: any) => {
         this.data = resp.resultado;
      },
      error: (err: any) => {
          console.log("error en consulta de parametros");
      }
    });
  }

  obtenerFormularioEditar(id:number){
    this.router.navigate(['/sistema/editar',id])
  }

  validaBotonEditar(nombre: string){
    const nombres = new Map([
      ["GENERAL", false],
      ["PREVISIONAL", false],
      ["ARL", false],
      ["VITALICIA", false],
      ["COD_PERSONA_JURIDICA", false]
   ]);
   return nombres.get(nombre) == undefined ? true : false;
  }

}
