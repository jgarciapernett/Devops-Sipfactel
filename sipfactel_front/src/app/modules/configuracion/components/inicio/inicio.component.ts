import { Component, OnInit } from '@angular/core';
import { FormsModule, FormControl } from '@angular/forms';
import { Subject } from 'rxjs/internal/Subject';

//#region rutas

import { Router } from '@angular/router';
//#endregion servicios

//#region servicios
import { ConfiguracionService } from './../../../../services/configuracion.service';
import { UtilidadesService } from '../../../../services/utilidades.service';
import { PaginacionService } from '../../../../services/paginacion.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
//#endregion servicios

//#region modelos
import { Lista } from '../../../../models/lista';
import { opcion } from 'src/app/models/opcion';
//#endregion model;

//#region animaciones
import { fundido } from 'src/app/animation/animation';
//#endregion animaciones

//#region bibliotecas
import Swal from 'sweetalert2';
import * as $ from 'jquery';
import * as _ from 'lodash';
import { finalize } from 'rxjs/operators';
import { debug } from 'util';
import { config } from 'rxjs';
//#endregion bibliotecas

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  providers: [ConfiguracionService, UtilidadesService, PaginacionService]
})

export class InicioComponent implements OnInit {

  public opcionesFiltro$: Subject<opcion[]> = new Subject<opcion[]>();
  public idPadre: number;
  public resultado: string;
  public opcionesLista: Lista[];
  public opcion: opcion[] = [];
  public opcionAEditar: opcion;
  public texto: string;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;
  public opcionElegida: number;
  public mostrar: boolean;
  public refreshLista: any;
  pagina = 1;
  paginaTamano = 5;
  cantidadColeccion: number;
  paginaPrevia: any;

  public filtroTabla = new FormControl(' ');
  id: any;

  constructor(
    private configService: ConfiguracionService,
    private utilidadesService: UtilidadesService,
    private paginacionService: PaginacionService,
    private SpinnerService: Ng4LoadingSpinnerService,
    private router: Router
  ) {
    this.inicializarEventos();
    this.ObtenerPermisos('configuracion')
  }

  ngOnInit() {
    this.opcionElegida = 0;
    this.mostrar = false;
    this.obtenerOpcionesLista();
  }
  

  obtenerOpcionesLista() {
    this.configService.obtenerListaDeOpciones().subscribe((res: any) => {
      this.resultado = JSON.stringify(res);
      this.opcionesLista = res.resultado;

      this.paginacionService.params.cantidadColeccion = this.resultado.length;
      this.aplicarFiltroTabla();
      this.mostrarFilasPagina();
    });
  }


  obtenerDataOpciones(refresh: any) {
    
    if (this.opcionElegida !== 0) {
      this.configService
        .obtenerOpcionElegida(this.opcionElegida)
        .subscribe((resp: any) => {
          this.resultado = JSON.stringify(resp);
          this.opcion = resp.resultado;
        });
      this.mostrar = true;
    } else {
      this.mostrar = false;
    }
  }

  aplicarFiltroTabla() {
    this.utilidadesService.filtrarTabla(
      this.filtroTabla,
      this.opcion,
      this.opcionesFiltro$
    );
  }

  cargarPagina(pagina: number) {
    this.paginacionService.cargarPagina(
      pagina,
      this.opcionesFiltro$,
      this.opcion
    );
  }

  mostrarFilasPagina() {
    this.paginacionService.mostrarFilasPagina(
      this.opcionesFiltro$,
      this.opcion
    );
  }

  abrirModalAgregar() {
    const prueba = document.getElementById('tipoDocId') as HTMLSelectElement;
    const texto = prueba.options[prueba.selectedIndex].text;
    this.idPadre = this.opcionElegida;
    this.texto = texto;
    $('#agregarConfiguracionModal').modal('show');

  }

  // Se agrega el modal hide de editar
  cerrarModalHide(mensaje) {
    $('#editarConfiguracionModal').modal('hide');
    $('#agregarConfiguracionModal').modal('hide');
  }

  abrirModalEditar(id: number) {
    this.configService.obtenerOpcionPorId(id).subscribe((res: any) => {
      this.opcionAEditar = res.resultado;
    });
    $('#editarConfiguracionModal').modal('show');
  }

  inicializarEventos() {

    $('#agregarConfiguracionModal').on('hidden.bs.modal', evt => { });
    $('#editarConfiguracionModal').on('hidden.bs.modal', evt => { });
  }

  ObtenerPermisos(menuAlias: string) {
   
    let objsesion = JSON.parse(localStorage.getItem('sesion'));
    let menu = objsesion.listMenu;
    menu.forEach(item => {
      
      if (item.menuAlias === menuAlias) {

        this.esCrear = item.crear;
        this.esConsultar = item.consultar;
        this.esEditar = item.modificar;
      }
    });


  }
}
