import { Component, OnInit, SimpleChanges } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';

//#region modelos
import { Compania } from 'src/app/models/compania/compania';
import { tipoOperacion } from 'src/app/models/compania/compania-tipoOperacion';
import { DatosCompania } from 'src/app/models/compania/compania-datosCompania';
import { datosGenerales } from 'src/app/models/compania/compania-datosGenerales';
//#endregion modulos

//#region servicios
import { CompaniasService } from 'src/app/services/companias.service';
import { ToastrService } from 'ngx-toastr';
//#endregion servicios

//#region rutas
import { Router } from '@angular/router';
//#endregion servicios
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public paso: number;
  public compania: Compania;
  public datosTipo: tipoOperacion;
  public datosCompania: DatosCompania;
  public datosGenerales: datosGenerales;
  public esAgregar: boolean;
  public cambioDeFondoEditar: boolean;
  public cambioDeFondoAgregar: boolean;
  public cancelar: boolean;
  public Agregar: boolean;
  public Editar: boolean;
  public formulario: boolean;
  public limpiarFormulario: any;
  public tipo: boolean;
  public oblig: boolean;
  public dato: boolean;
  public general: boolean;
  public valor: string;
  public titulo: string;
  public contenido: string;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;


  constructor(
    public companiaService: CompaniasService,
    private toastr: ToastrService,
    public steps: NgbProgressbarConfig
  ) {
    this.inicializacion();
    this.inicializacionDeParametros();
    this.ObtenerPermisos('companias')
  }

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges): void {
    this.botonCancelar();
    changes.fondo.currentValue != true;
    if (changes.paso.currentValue != undefined) {
    }
  }

  inicializacionDeParametros() {
    this.tipo = true;
    this.dato = true;
    this.general = true;
    this.valor = '';
    this.titulo = '';
    this.contenido =
      'Se limpia el formulario y se habilitan opciones agregar y editar.';
  }

  ocultarComponente($event) {
    if ((this.dato = $event.selected)) {
      this.dato = false;
    }
    this.tipo = true;
  }

  ocultarSegundoComponente($event) {
    if ((this.tipo = $event.selected)) {
      this.tipo = false;
    }
    this.general = true;
  }

  ActualizarDatosCompania($event) {
    
    this.titulo = 'Tipo Operación';
    this.valor = '2';
    this.paso = $event.paso;
    this.datosCompania = $event.datosCompania;
    this.compania = Object.assign(this.compania, this.datosCompania);
  }

  ActualizarTipoOperacion($event) {
    
    this.titulo = 'Datos Generales';
    this.valor = '3';
    this.oblig = $event.oblig
    this.paso = $event.paso;
    this.datosTipo = $event.tipoOperacion;
    this.compania = Object.assign(this.compania, $event.tipoOperacion);
    this.actualizarPasoAtras($event.paso);
  }

  actualizarDatosGenerales($event) {
    
    this.titulo = 'Tipo Operación';
    this.valor = '1';
    this.paso = $event.paso;
    this.datosGenerales = $event.datosGenerales;
    this.compania = Object.assign(this.compania, $event.datosGenerales);
    if ( $event.paso === 2 ) {
      this.actualizarPasoAtras($event.paso);
    } else if (this.compania.comp == 0 && this.compania != null && $event.paso != 2) {
      this.insertarDatosCompania();
    } else if (this.compania.comp !== 0 && this.compania !== undefined && $event.paso != 2 ) {
    this.editarDatosCompania();
    }
  }

  insertarDatosCompania() {
    this.companiaService.insertarCompania(this.compania).subscribe(
      (resp: any) => {
        this.cambioDeFondoAgregar = false;
        this.formulario = false;
        this.cancelar = false;
        this.Editar = false;
        
        Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: 'Registro guardado correctamente'
        });
        
        this.inicializacion();
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  editarDatosCompania() {
    this.companiaService
      .editarCompania(this.compania)
      .subscribe((resp: any) => {
        if (resp.mensajeEstado == 'Ok') {
          this.cambioDeFondoEditar = false;
          this.formulario = false;
          this.cancelar = false;
          this.Agregar = false;
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: 'Registro guardado correctamente'
        });
          this.inicializacion();
        }
      });
  }

  cargarCompaniaEditar($event) {
    this.companiaService
      .obtenerOpcionListaPorId($event)
      .subscribe((res: any) => {
        let CompaniaEditar: Compania = Object.assign(
          new Compania(),
          res.resultado
        );
        this.compania = CompaniaEditar;
      });
  }

  obtenerFormularioAgregar() {
    this.datosCompania = new DatosCompania();
    this.esAgregar = false;
    this.cambioDeFondoAgregar = true;
    this.cambioDeFondoEditar = false;
    this.valor = '1';
    this.titulo = 'Datos Compañía';
    this.cancelar = true;
    this.Editar = true;
    this.formulario = true;
  }

  obtenerFormularioEditar() {
    this.esAgregar = true;
    this.cambioDeFondoEditar = true;
    this.cambioDeFondoAgregar = false;
    this.valor = '1';
    this.titulo = 'Datos Compañía';
    this.cancelar = true;
    this.Agregar = true;
    this.formulario = true;
  }

  inicializacion() {
    this.paso = 1;
    this.compania = new Compania();
    this.tipo = false;
    this.dato = true;
    this.general = false;
    this.steps.max = 3;
    this.steps.striped = true;
    this.steps.animated = true;
    this.steps.type = 'success';
    this.steps.height = '20px';
    this.valor = '';
    this.formulario = false;
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
  botonCancelar() {
    const limpiarFormulario = (this.compania = new Compania());
    this.paso = 1;
    this.formulario = false;
    this.valor = '';
    this.cambioDeFondoAgregar = false;
    this.cambioDeFondoEditar = false;
    this.Agregar = false;
    this.Editar = false;
    this.esAgregar = undefined;
  }

  actualizarPasoAtras(paso) {
    
    this.paso = paso;
    if (this.paso == 1) {
      this.titulo = 'Datos Compañía';
      this.valor = '1';
    } else if (this.paso == 2) {
      this.titulo = 'Tipo Operacion';
      this.valor = '2';
    }
  }
}
