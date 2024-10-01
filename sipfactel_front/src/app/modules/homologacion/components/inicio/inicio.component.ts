import { Component, OnInit } from "@angular/core";
import {  FormControl } from "@angular/forms";
import { Subject } from "rxjs/internal/Subject";

// rutas

// #region servicios
import { UtilidadesService } from "../../../../services/utilidades.service";
import { homologacionService } from '../../../../services/homologacion.service';
import { PaginacionService } from '../../../../services/paginacion.service';
// #endregion servicios

//#region modelos

import { Lista } from "../../../../models/lista";
import { homologacion } from "src/app/models/homologacion";

//#endregion;

//#region animaciones

import { fundido } from "src/app/animation/animation";
//#endregion animaciones

//#region bibliotecas
import Swal from "sweetalert2";
import * as $ from "jquery";
import * as _ from "lodash";
import { HttpErrorResponse } from '@angular/common/http';


//#endregion bibliotecas

@Component({
  selector: "app-inicio",
  templateUrl: "./inicio.component.html",
  providers: [homologacionService,UtilidadesService,PaginacionService]
})

export class InicioComponent implements OnInit {

  public opcionesFiltro$: Subject<homologacion[]> = new Subject<homologacion[]>();
  public homologaciones: Lista [];
  public homologacion: homologacion [] = [];
  public  fuentes : Lista []
  public idPadre: number;
  public resultado: string;
  public opcionAEditar: homologacion;
  public homologacionElegida: number;
  public mostrar: boolean;
  public id: number;
  public regreso:any;
  public filtroTabla = new FormControl(" ");
  public VolverTablaHomologaciones:any
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;

  

  constructor(
    private homolService: homologacionService,
    private utilidadesService: UtilidadesService,
    private PaginacionService:PaginacionService,
  ) {

    this.obtenerFuente()
    this.inicializarEventos();
    this.ObtenerPermisos('homologacion');

 
  }

  ngOnInit() {
    this.homologacionElegida = 0;
    this.mostrar= false;
    this.obtenerOpcionLista();
  
  }


  obtenerOpcionLista() {  // Se consume un servicio que pinta las homologaciones del desplegable
    
    this.homolService.obtenerLista().subscribe((res: any) => {
      this.resultado = JSON.stringify(res);
      this.homologaciones = res.resultado;
      this.PaginacionService.params.cantidadColeccion = this.resultado.length;
      this.aplicarFiltroTabla();
      this.mostrarFilasPagina();
    });

  }
  obtenerFuente(){
    this.homolService.obtenerListaFuentes().subscribe((res:any ) => {
      this.fuentes = res.resultado
      console.log(this.fuentes)
    })
  }
  ObtenerPermisos(menuAlias: string) { // Valida si el usuario actual tiene permisos para ingresar a las pantallas

    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    menu.forEach(item => {
    
      if (item.menuAlias === menuAlias) {

        this.esCrear = item.crear;
        this.esConsultar = item.consultar;
        this.esEditar = item.modificar;
      }
    });


  }
  eliminar(id : number){
    Swal.fire ( {      // Mensaje de confirmar o cancelar la eliminacion de un registro

      title: '¿Esta seguro de eliminar esta homologación?',
      type: 'question',
      showConfirmButton: true,
      confirmButtonColor: '#149275',
      cancelButtonColor: '#dc3545',
      showCancelButton : true,
    } ).then(resp =>{   // Validacion de eliminar Si se preciona el boton OK se consume el servicio y se elimina el registro
      if (resp.value)
      {  
        this.homolService.eliminar(id).subscribe(
          (res:any) => {
          this.obtenerHomologaciones();
          this.VolverTablaHomologaciones.emit('agrego');
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: res.resultado
          });
        },
        (error:HttpErrorResponse) => {},
        );
        }
      else if (resp.dismiss === Swal.DismissReason.cancel) { //Validacion del boton cancelar para que vuelva a mostrar la tabla
        this.obtenerHomologaciones();
      }
    })
  }

  obtenerHomologaciones() {  // Lista la informacion en la tabla 

    if (this.homologacionElegida !== 0) {// Valida que el parametro venga diligenciado
      this.homolService
      .obtenerHomologacionElegida (this.homologacionElegida)
      .subscribe((resp: any) => { // Solicitud con exitó o fallo
        this.resultado = JSON.stringify(resp);
        this.homologacion = resp.resultado;
      });
      this.mostrar = true;
    } else {
      this.mostrar = false;
    }
    
  }
  
  actualizarDatosHomologacion(){// Cuando se agrege o se edite actualiza la tabla automaticamente
 
    
    $('#agregarHomologacionModal').modal('hide');
    $('#editarHomologacionModal').modal('hide');


  }


  aplicarFiltroTabla() { // Filtor de busqueda en la tabla
    this.utilidadesService.filtrarTabla(
      this.filtroTabla,
      this.homologacion,
      this.opcionesFiltro$
    );
  }
  
  cargarPagina(pagina: number) { // Muestra el numero de paginas de registros
    this.PaginacionService.cargarPagina(
      pagina,
      this.opcionesFiltro$,
      this.homologacion
    );
  }

  mostrarFilasPagina() { // Muesta el numero de filas que tiene la tabla
    this.PaginacionService.mostrarFilasPagina(
      this.opcionesFiltro$,
      this.homologacion
    );
  }

  abrirModalAgregar(){ // Muesta el formulario de agregar homologacion
  
  this.idPadre = this.homologacionElegida;
    $('#agregarHomologacionModal').modal('show');
  }

  abrirModalEditar (parametrosOpcion:homologacion) { // Muesta el formulario de editar homologacion
   
   this.obtenerHomologaciones();
    this.opcionAEditar = parametrosOpcion;
    $('#editarHomologacionModal').modal("show")
  }
  
    inicializarEventos() { // inicializacion de eventos para que los formularios no se muestren
    
      $('#agregarHomologacionModal').on('hidden.bs.modal', (evt) => {    });
      $('#editarHomologacionModal').on('hidden.bs.modal', (evt) => {    });
    }
}
