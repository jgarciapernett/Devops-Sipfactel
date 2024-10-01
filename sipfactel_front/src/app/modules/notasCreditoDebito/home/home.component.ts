import { Component, OnInit } from '@angular/core'
import { ResolucionNotas } from '../../../models/resoluciones/ResolucionNotas';
import { NotasDebitoCredito } from '../../../models/resoluciones/notasDebitoCredito';
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { selectInicial } from './../../../directives/validators/select.validator';
import { ReportesService } from 'src/app/services/reportes.service';
import { SucursalService } from 'src/app/services/sucursal.service';
import { ResolucionesNotasService } from 'src/app/services/resolucionesNotas.service';
import { sucursal } from '../../../models/desplegableSucu';
import { Lista } from 'src/app/models/lista';

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html"
})
export class HomeComponent implements OnInit {

  public mostrarDebitoCredito: boolean = false;
  public jsonNotas: ResolucionNotas;
  public listaSucursales: sucursal[];
  public listaCompanias: Lista[];
  public productos: Lista[];
  public default: number = 0;
  public default2: number = 0;
  public formConsulta: FormGroup;
  public formEnvio: FormGroup;
  public debitoCredito: boolean;
  public tablaNotas: NotasDebitoCredito[] = null;
  public tipoCredito: String = "credito";
  public tipoDebito: String = "debito";
  public tipoNotaConsultada: String;

  public sucursal: number;
  public compania: number;
  public producto: string;

  public esCrear: boolean;
  public esConsultar: boolean;
  public esEditar: boolean;

  constructor(
    public reportesService: ReportesService,
    public resolucionesNotasService: ResolucionesNotasService,
    public formGroup: FormBuilder,
    public sucursalServices: SucursalService,
    public formBuilder: FormBuilder
  ) {
    this.validacionesFormularioConsulta();
    this.validacionesFormularioEnvio();
    this.ObtenerPermisos("nota");
  }


  ngOnInit() {
    this.ObtenerListaSucursales();
    this.obtenerListaCompanias();
    this.obtenerListaTiposPolizas();
  }

  //funcion que mostrar el formulario
  public verFormulario(form: NgForm): void {
    this.sucursal = form.value.lSucursales;
    this.compania = form.value.lCompanias;
    this.producto = form.value.lProductos;
    this.resolucionesNotasService.obtenerResolucionNotas(this.sucursal, this.compania, this.producto).subscribe(
      (resp: ResolucionNotas) => {
        this.jsonNotas = resp;
        this.tablaNotas = null;
        this.tipoNotaConsultada = null;
        this.mostrarDebitoCredito = true;
      }
    );
  }

  public reiniciarServicio(reinicio: boolean) {
    if (reinicio) {
      this.resolucionesNotasService.obtenerResolucionNotas(this.sucursal, this.compania, this.producto).subscribe(
        (resp: ResolucionNotas) => {
          this.jsonNotas = resp;
          this.tablaNotas = null;
          this.tipoNotaConsultada = null;
          this.mostrarDebitoCredito = true;
        }
      );
    }
  }

  public ObtenerListaSucursales(): void { // lista  todas las sucursales
    this.reportesService.listaSucursales().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.listaSucursales = resp.resultado;
    });
  }

  public obtenerListaCompanias(): void {
    this.sucursalServices.obtenerListaCompanias().subscribe((res: any) => {
      this.listaCompanias = res.resultado;
    });
  }

  public obtenerListaTiposPolizas(): void {
    this.sucursalServices.obtenerListaTiposPoliza().subscribe((res: any) => {
      this.productos = res.resultado;
    });
  }

  private validacionesFormularioConsulta() {
    this.formConsulta = this.formBuilder.group({
      lCompanias: this.formGroup.control(0, [Validators.required, selectInicial]),
      lSucursales: this.formGroup.control(0, [Validators.required, selectInicial]),
      lProductos: this.formGroup.control(0, [Validators.required, selectInicial])
    });
  }

  private validacionesFormularioEnvio() {
    this.formEnvio = this.formBuilder.group({
      tidcred: this.formGroup.control(null, [Validators.required]),
      ncprefijo: this.formGroup.control('', [Validators.required]),
      ncnumini: this.formGroup.control(null, [Validators.required]),
      ncnumfin: this.formBuilder.control(null, [Validators.required]),
      tiddeb: this.formGroup.control(null, [Validators.required]),
      ndprefijo: this.formBuilder.control('', [Validators.required]),
      ndnumini: this.formBuilder.control(null, [Validators.required]),
      ndnumfin: this.formBuilder.control(null, [Validators.required])
    });
  }

  /*
    Metoto que mostrara una grilla con la informacion de resoluciones dependiendo de si son debito o credito
    @Param tipoBoton, String => degine si se vera debito o credito
   */

  public mostrarTabla(tipoBoton: String) {
    if (tipoBoton == "debito") {
      this.tablaNotas = this.jsonNotas.debito;
      this.tipoNotaConsultada = this.tipoDebito;
    } else {
      this.tablaNotas = this.jsonNotas.credito;
      this.tipoNotaConsultada = this.tipoCredito;
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
      });
    });
  }

}