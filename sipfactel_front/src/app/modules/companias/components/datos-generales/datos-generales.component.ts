import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SimpleChanges,
} from "@angular/core";
import { NgForm, FormGroup, FormBuilder, Validators } from "@angular/forms";

//#region servicios
import { UtilidadesService } from "../../../../services/utilidades.service";
import { CompaniasService } from "../../../../services/companias.service";
import { ToastrService } from "ngx-toastr";
//#endregion

//#region modelos
import { datosGenerales } from "src/app/models/compania/compania-datosGenerales";
import { Compania } from "src/app/models/compania/compania";
import { tipoOperacion } from "src/app/models/compania/compania-tipoOperacion";
//#endregion

//#region rutas
import { Router } from "@angular/router";
import { DatosCompania } from "src/app/models/compania/compania-datosCompania";
import { Lista } from "src/app/models/lista";
import { selectInicial } from "src/app/directives/validators/select.validator";
import { Porcentaje } from '../../../../models/compania/porc';
//#endregion

@Component({
  selector: "compania-datos-generales",
  templateUrl: "./datos-generales.component.html",
  styleUrls: ["./datos-generales.component.css"],
})
export class DatosGeneralesComponent implements OnInit {
  @Input() companiaDatosGenerales: Compania;
  @Output() general = new EventEmitter<Object>();
  @Output() paso = new EventEmitter<number>();
  @Input() esAgregar: boolean;

  public Cambiofondo = false;
  public ngAgregarCompania: NgForm;
  public frmAgregarDatosGenerales: FormGroup;
  public Tributo: Lista[] = [];
  public codTributo: number;

  public operacionGeneral: datosGenerales;
  public infoFormularioCompania: Compania;
  public datosCompania: DatosCompania;
  public datosTipo: tipoOperacion;
  public listaRegimen: Lista[];
  public listaMetodoPago: Lista[];
  public listaUnidadMedida: Lista[];
  public listaFormaPago: Lista[];
  public listaProductos: Lista[];
  public listaTipoTributo: Lista[];
  public listaCodReg: Lista[];
  public listaPorcentajeAtributo: Porcentaje [];

  constructor(
    public formBuilder: FormBuilder,
    public companiaService: CompaniasService,
    public utilidadesService: UtilidadesService
  ) {
    this.operacionGeneral = new datosGenerales();
  }

  ngOnInit() {
    this.editarOAgregar();
    this.Tributos()
    this.obtenerFormaPago();
    this.obtenerMetodoPago();
    this.obtenerPorcentajeAtributo();
    this.obtenerProductos();
    this.obtenerRegimenFiscal();
    this.obtenerTipoTributo();
    this.obtenerUnidadMedida();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.companiaDatosGenerales.currentValue !== undefined) {
      if (changes.companiaDatosGenerales.currentValue.comp !== undefined) {
        this.AsignacionCompaniaADatosCompania(
          changes.companiaDatosGenerales.currentValue
        );
      }
    }
  }

  AsignacionCompaniaADatosCompania(objCompania: Compania) {
    
    this.operacionGeneral = Object.assign(new datosGenerales(), objCompania);
    
  }    

  agregarCompaniaDatosGenerales() {
    
    this.listaTipoTributo.map(nombre => 
      {
        if(this.operacionGeneral.codatributo === nombre.id){
          this.operacionGeneral.nomatributo = nombre.nombre
        }
      })
    var objRespuesta = new Object({
      paso: 4,
      datosGenerales: this.operacionGeneral,
 
    });
    this.general.emit(objRespuesta);
  }

  volverAtras() {
    var objRespuesta = new Object({
      paso: 2,
      datosGenerales: this.operacionGeneral,
    });
    this.general.emit(objRespuesta);
  }

  obtenerRegimenFiscal() {
    this.companiaService.listaRegimenesFiscales().subscribe((res: any) => {
      this.listaRegimen = res.resultado;
    });
  }

  obtenerMetodoPago() {
    this.companiaService.listaMetodoPago().subscribe((res: any) => {
      this.listaMetodoPago = res.resultado;
    });
  }

  obtenerUnidadMedida() {
    this.companiaService.listaUnidadMedida().subscribe((res: any) => {
      this.listaUnidadMedida = res.resultado;
    });
  }

  obtenerFormaPago(){
    this.companiaService.listaFormaPago().subscribe( (res: any ) =>{
      this.listaFormaPago = res.resultado;
    });
  }

  obtenerProductos(){
    this.companiaService.listaProductos().subscribe( (res: any ) =>{
      this.listaProductos = res.resultado;
    });
  }

  obtenerTipoTributo(){
    
    this.companiaService.listaTipoTributo().subscribe( (res: any ) =>{
      this.listaTipoTributo = res.resultado;
    });
  }

  obtenerPorcentajeAtributo(){
    
    this.companiaService.listaPorcentajeAtributo().subscribe( (res: any ) =>{
      this.listaPorcentajeAtributo = res.resultado;
    });
  }
  Tributos() {
    this.companiaService.obtenerTributo().subscribe((resp: any) => {
      this.Tributo = resp.resultado;
    });
  }
  validacionesFormulario() {
    this.frmAgregarDatosGenerales = this.formBuilder.group({
      txtCodEmpresa: this.formBuilder.control("", [Validators.required]),
      ddlPorAtributo: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlCodAtributo: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlCodEstandar: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlMetodoPago: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      txttributo: this.formBuilder.control("", [Validators.required]),
      ddlUniMedida: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      txtCantidad: this.formBuilder.control("", [Validators.required]),
      ddlForPago: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlRegimen: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlcodRegimen: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
    });

  }
  
  validacionesFormularioEditar() {
    this.frmAgregarDatosGenerales = this.formBuilder.group({
      // txtCodEmpresa: this.formBuilder.control("", [Validators.required]),
      ddlPorAtributo: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      // ddlCodAtributo: this.formBuilder.control(0, [
      //   Validators.required,
      //   selectInicial,
      // ]),
      ddlCodEstandar: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlMetodoPago: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      txttributo: this.formBuilder.control(0, [Validators.required,selectInicial]),
      ddlUniMedida: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      txtCantidad: this.formBuilder.control("", [Validators.required]),
      ddlForPago: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlRegimen: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      // ddlcodRegimen: this.formBuilder.control(0, [
      //   Validators.required,
      //   selectInicial,
      // ]),
    });
  }
  editarOAgregar(){
    
    if(this.esAgregar == true){
      this.validacionesFormularioEditar()

    }else if(this.esAgregar == false){
      this.validacionesFormulario()

    }
  }
}
