import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AdquirientesService } from 'src/app/services/adquirientes.service';


// Modelos
import { Adquirientes } from '../../../models/adquirientes/TipoDocumento';
import { Subject } from 'rxjs';
import { TablaAdqui } from '../../../models/adquirientes/tablaAdquirientes';
import { Adquiriente } from 'src/app/models/adquirientes/adquiriente';
import { Lista } from 'src/app/models/lista';
import { finalize } from 'rxjs/operators';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public mostarNavEditar: boolean;


  public opcionesFiltro$: Subject<TablaAdqui[]> = new Subject<TablaAdqui[]>();
  public opcion: Adquirientes[];
  public datosEdicion: Adquiriente;
  public listaIdentificacion: Lista[];
  public tablaFiltro: boolean;
  public frmAdquirientes: FormGroup;
  public tDoc: number;
  public nDoc: number;
  public rSocial: string;
  public resultado: string;
  public dato: boolean;
  public home: boolean;
  public nombre: string;
  public idEdicion: number;
  public razon: string;
  public numeroDoc: number;
  public tipo: number;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;

  constructor(
    public formGroup: FormBuilder,
    private adquirienteService: AdquirientesService,
    private SpinnerService: Ng4LoadingSpinnerService,
    public router: Router
  ) {
    this.tipoDocumento();
    this.nDoc = 0;
    this.ObtenerPermisos('adquirientes')
  }

  ngOnInit() {
    this.home = true;
    this.tablaFiltro = false;
    this.tDoc = 0;
    this.nDoc = 0;
    this.rSocial = '';

  }


  filtroAdquiriente() {
    this.SpinnerService.show();
    if (this.tDoc !== 0 && this.nDoc === 0 && this.rSocial === '') {
      this.nombre = 'tipoDoc';
      this.tDoc = this.tDoc;

    } else if (this.nDoc !== 0 && this.tDoc === 0 && this.rSocial === '') {
      this.nombre = 'numeroDoc';
      this.numeroDoc = this.nDoc;

    } else if (this.rSocial !== '' && this.tDoc === 0 && this.nDoc === 0) {
      this.nombre = 'razonSocial';
      this.razon = this.rSocial;

    } else if (this.tDoc !== 0 && this.nDoc !== 0) {
      this.nombre = 'Tdoc&nDoc';
      this.tipo = this.tDoc;
      this.numeroDoc = this.nDoc;

    } else if (this.tDoc !== 0 && this.rSocial !== '') {
      this.nombre = 'Tdoc&razon';
      this.tipo = this.tDoc;
      this.razon = this.rSocial;

    }
    
    this.adquirienteService
      .filtroAdquiriente(this.tDoc, this.nombre, this.razon, this.numeroDoc)
      .subscribe((resp: any) => {

        this.SpinnerService.show();
        this.resultado = JSON.stringify(resp);


        this.SpinnerService.show();
        this.opcion = resp.resultado;

        if (this.opcion.length === 0) {
          Swal.fire({
            type: 'info',
            title: 'Atención',
            text: 'No existe información asociada',
            footer: ''
          });


        }
        this.SpinnerService.hide()
        if (this.opcion != null) {
          this.SpinnerService.show();
          this.tablaFiltro = true;
          this.SpinnerService.hide()
        } else {
          this.tablaFiltro = false;
        }
      });
    //
  }

  cargarAdquirienteEditar(idEdicion) {
    this.router.navigate(['/Adquirientes/editar', idEdicion]);

  }


  tipoDocumento() {
    this.adquirienteService
      .obtenerListaIdentificacion()
      .subscribe((resp: any) => {
        this.resultado = JSON.stringify(resp);
        this.listaIdentificacion = resp.resultado;
        
      });
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



}
