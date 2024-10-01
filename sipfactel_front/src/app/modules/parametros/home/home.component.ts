import { Component, OnInit } from '@angular/core';


//#region servicios

import { parametrosService } from '../../../services/parametros.service';

//#endregion servicios

//#region modelos
import { parametrosGenerales } from 'src/app/models/ParametrosGenerales';
import { parametrosProveedor } from 'src/app/models/parametros';
//#endregion modelos

//#region bibliotecas
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
//#endregion bibliotecas
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  public tituloHome:boolean;
  public tituloGenerales:boolean;
  public tituloProveedor:boolean;
  public EsAgregar: boolean;
  public EsAgregar2: boolean;
  public cambioDeFondoAgregarDG: boolean;
  public cambioDeFondoAgregarDP: boolean;
  public cancelar: boolean;
  public proveedor: boolean;
  public dato: boolean;


  public paso: number;
  public resultado: string;

  public parametrosGeneralesObj: parametrosGenerales;
  public parametrosProveedorObj: parametrosProveedor;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;

  constructor(
    private parametroGService: parametrosService,
    private parametroPService: parametrosService
  ) {
    this.inicializacion();
    
  }
  
  ngOnInit() {
    
    this.ObtenerPermisos('parametros');
  }

  inicializacion() { // inicializacion de parametros en pantalla
    this.dato = true;
    this.paso = 1;
    this.tituloHome = true;
    this.tituloGenerales = false;
    this.tituloProveedor= false;
  }

  obtenerFormularioAgregarDatosGenerales() { // trae el formulario datos generales y su mensaje
    if ((this.paso = 1)) {
      this.EsAgregar2 = false;
      this.EsAgregar = true;
      this.cancelar = true;
      this.cambioDeFondoAgregarDP = false;
      this.cambioDeFondoAgregarDG = true;
      this.tituloGenerales = true;
      this.tituloHome = false;
      this.tituloProveedor= false;
    
    }
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
  obtenerFormularioAgregarDatosProveedor() {// Trae el formulario datos porveedor y su respectivo mensaje
    if ((this.paso = 2)) {
      this.EsAgregar2 = true;
      this.EsAgregar = false;
      this.cancelar = true;
      this.cambioDeFondoAgregarDP = true;
      this.cambioDeFondoAgregarDG = false;
      this.tituloProveedor= true;
      this.tituloGenerales = false;
      this.tituloHome = false;
    }
  }


  botonCancelar() { // Cancela la operacion y regesa a la pantalla inicial
    this.paso = 1;
    this.EsAgregar = undefined;
    this.EsAgregar2 = undefined;
    if ((this.paso = 1)) {
      this.cancelar = false;
    }
    this.cambioDeFondoAgregarDP = false;
    this.cambioDeFondoAgregarDG = false;
  }


  AgregarDatosGenerales($event) { // Evento para mostrar la informacion de datos generales en la pantalla inicial
    this.paso = $event.paso;
    this.parametrosGeneralesObj = $event.parametrosGenerales;
    this.agregarDatosGenerales();
  }


  agregarDatosGenerales() { // Servicio que agrega un nuevo registro de datos generales
    this.parametroGService
      .AgregarDatosGenerales(this.parametrosGeneralesObj)
      .subscribe(
        (res: any) => { // Solicitud con exitó o fallo
          this.EsAgregar2 = undefined;
          this.EsAgregar = undefined;
          this.cancelar = false;
          this.cambioDeFondoAgregarDG = false;
          this.parametrosGeneralesObj = new parametrosGenerales();
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: res.resultado
          });
          this.inicializacion();
        },
        (error: HttpErrorResponse) => {}
      );
  }


  AgregarDatosProveedor($event) { // Evento que muestra la informacion en la pantalla onicial de datos proveedor
    this.paso = $event.paso;
    this.parametrosProveedorObj = $event.parametrosProveedor;
    this.agregarDatosproveedor();
  }


  agregarDatosproveedor() { // Servio que agrega un nuevo registro de datos proveedor
    this.parametroPService
      .AgregarDatosproveedor(this.parametrosProveedorObj)
      .subscribe( // Solicitud con exitó o fallo
        (res: any) => {
          this.EsAgregar2 = undefined;
          this.EsAgregar = undefined;
          this.cancelar = false;
          this.cambioDeFondoAgregarDP = false;
          this.parametrosProveedorObj = new parametrosProveedor();
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: res.resultado,
          });
        },
        (error: HttpErrorResponse) => {
        }
      );
  }

}
