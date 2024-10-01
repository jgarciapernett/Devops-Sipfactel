import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SimpleChanges,
  ViewChild
} from "@angular/core";
import { NgForm, FormGroup, FormBuilder, Validators } from "@angular/forms";

//#region servicios
import { UtilidadesService } from "../../../../services/utilidades.service";
import { CompaniasService } from "../../../../services/companias.service";
import { ToastrService } from "ngx-toastr";
//#endregion

//#region modelos
import { DatosCompania } from "../../../../models/compania/compania-datosCompania";
import { Compania } from "../../../../models/compania/compania";
import { Lista } from "src/app/models/lista";
//#endregion

//#region rutas
import { Router } from "@angular/router";
import { codigoPostal } from 'src/app/models/compania/codigoPostal';
import { selectInicial } from 'src/app/directives/validators/select.validator';
import { TipoOperacionComponent } from '../tipo-operacion/tipo-operacion.component';
import { DebugElement } from '@angular/core';
//#endregion

@Component({
  selector: "compania-datos-compania",
  templateUrl: "./datos-compania.component.html",
  styleUrls: ["./datos-compania.component.css"],
  providers: [CompaniasService]
})
export class DatosCompaniaComponent implements OnInit {
  @Output() limpiarFormulario = new EventEmitter<any>();
  @Output() idCompaniaEditar = new EventEmitter<number>();
  @Input() esAgregar: boolean;
  @Output() dato = new EventEmitter<Object>();
  @Input() companiaEditar: Compania;
  @Input() tipo: boolean;
  @Input() oblig: boolean

  public esObligacionValido: boolean;
  public ngAgregarCompania: NgForm;
  public frmAgregarDatosCompania: FormGroup;
  public listaCompanias: Lista[];
  public listaPaises: Lista[];
  public listaTipoDocumento: Lista[];
  public listaTipoPersona: Lista[];
  public listaobligacionesFiscales: Lista[];
  public obligacion : Lista;
  public camposDatosCompania: DatosCompania;
  public opcionElegida: number;

  constructor(
    public formBuilder: FormBuilder,
    public companiaService: CompaniasService,
    public utilidadesService: UtilidadesService

    ) {
      this.esObligacionValido = false
    this.camposDatosCompania = new DatosCompania();
  }

  ngOnInit() {
    this.obtenerListaPaises();
    this.obtenerObligacionFiscal();
    this.obtenerTipoDocumento();
    this.obtenerTipoPersona();
    this.validacionesFormulario();
    this.cargarListaDelSeleccionable();
    // this.comprobarValideObligaciones()
  }

  ngOnChanges(changes: SimpleChanges): void {
    
    if (changes.companiaEditar.currentValue !== undefined) {
      if (changes.companiaEditar.currentValue.comp !== undefined) {
        this.AsignacionCompaniaADatosCompania(
          changes.companiaEditar.currentValue
        );
      }
    }
   
    
    if(changes.oblig.currentValue === true && this.esObligacionValido === true && this.camposDatosCompania.obligacionesfiscales.length !=0)
    {
      
       this.esObligacionValido = this.oblig
    }else if(changes.oblig.currentValue === true && this.esObligacionValido === true  && this.camposDatosCompania.obligacionesfiscales.length == 0)
    {
      
       this.esObligacionValido = false
    }else if(changes.oblig.currentValue === true && this.esObligacionValido === false && this.camposDatosCompania.obligacionesfiscales.length !=0)
    {
      
       this.esObligacionValido = this.oblig
    }else if(changes.oblig.currentValue === true && this.esObligacionValido === false  && this.camposDatosCompania.obligacionesfiscales.length == 0)
    {
      
       this.esObligacionValido = false
    }else if(changes.oblig.currentValue === undefined && this.esObligacionValido === false)
    {
      
       this.esObligacionValido = false
    }
  }

  esObligacionSeleccionada(rolId: string) { // Verifica los permisos seleccionados

    const esSeleccionado = this.camposDatosCompania.obligacionesfiscales.some((role: Lista) => {
      return role.id.toString() === rolId.toString();
    });
    return esSeleccionado;

  }
  validesOblig(){
    
    if(this.camposDatosCompania.obligacionesfiscales.length <= 0){
      this.esObligacionValido == false
    }
  }
  comprobarValideObligaciones() { // Valida que minimo un permiso este seleccionado
  

   
     if (this.esObligacionValido == false && this.camposDatosCompania.obligacionesfiscales.length == 0) {
      
      this.esObligacionValido = true
    }else if (this.camposDatosCompania.obligacionesfiscales.length != 0){

      this.esObligacionValido = true

    }
    else if(this.esObligacionValido == false && this.camposDatosCompania.obligacionesfiscales.length != 0) {
      
      this.esObligacionValido = true
    }else if(this.esObligacionValido == true && this.camposDatosCompania.obligacionesfiscales.length == 0){
      this.esObligacionValido = true

    }else if(this.esObligacionValido == true && this.camposDatosCompania.obligacionesfiscales.length != 0){
      this.esObligacionValido = true

    }
  }



  obligaciones(event: Event, idMenu: number, menuMenu: string, idstr: string,) { // función de los checkbox

    const eventTarget = event.target as HTMLInputElement;

    if (eventTarget.checked) { // Valida que la informacion del checkbox obtenida tenga los mismos paramtros del modelo
      this.obligacion = new Lista(idMenu, menuMenu, idstr,);
      this.camposDatosCompania.obligacionesfiscales.push(this.obligacion);
    }
    else {

      this.camposDatosCompania.obligacionesfiscales = this.camposDatosCompania.obligacionesfiscales.filter((role: Lista) => { // Elimina el array cuando se quita un check
        return role.id.toString() !== idMenu.toString();

      });
    }
    this.comprobarValideObligaciones();

  }

  AsignacionCompaniaADatosCompania(objCompania: Compania) {
   
    this.camposDatosCompania = Object.assign(new DatosCompania(), objCompania);
    this.limpiarFormulario.emit();
  }

  cambioDeOpcionElegida() {
    
    this.idCompaniaEditar.emit(this.camposDatosCompania.comp);
    if(this.camposDatosCompania.comp != 0){

      this.comprobarValideObligaciones()
      
    }else if (this.camposDatosCompania.comp === 0){
    this.camposDatosCompania = new DatosCompania();

    }
  }
  
  valides(){
    
  }
  cargarListaDelSeleccionable() {

    this.companiaService.obtenerOpcionesLista().subscribe((res: any) => {
      this.listaCompanias = res.resultado;
    });

  }

  obtenerListaPaises(){
    this.companiaService.listaPaises().subscribe((res: any)=>{
      this.listaPaises = res.resultado;
    });
  }

  obtenerTipoDocumento(){
    this.companiaService.listaTipoDocumento().subscribe((res: any)=>{
      this.listaTipoDocumento = res.resultado;
    });
  }

  obtenerTipoPersona(){
    this.companiaService.listaTipoPersona().subscribe((res: any)=>{
      this.listaTipoPersona = res.resultado;
    });
  }

  obtenerObligacionFiscal(){
    this.companiaService.listaObligacionesFiscales().subscribe((res: any)=>{
      this.listaobligacionesFiscales = res.resultado;
    });
  }

  agregarDatosCompania() {
    var objRespuesta = new Object({
      paso: 2,
      datosCompania: this.camposDatosCompania
    });
    this.dato.emit(objRespuesta);

  }

  validacionesFormulario() {
    this.frmAgregarDatosCompania = this.formBuilder.group({
      ddlCompanias: this.formBuilder.control(0, [Validators.required, selectInicial]),
      txtCodigoAscel: this.formBuilder.control("", [Validators.required]),
      ddlTipoDoc: this.formBuilder.control(0, [Validators.required, selectInicial]),
      ddlCodPais: this.formBuilder.control(0, [Validators.required, selectInicial]),
      // ddlObligFiscales: this.formBuilder.control(true, [Validators.required]),
      txtRazonSocial: this.formBuilder.control("", [Validators.required]),
      txtCodPoint: this.formBuilder.control("", [Validators.required]),
      txtDocumento: this.formBuilder.control("", [Validators.required]),
      txtNomPais: this.formBuilder.control("", [Validators.required]),
      ddlTipoPersona: this.formBuilder.control(0, [Validators.required, selectInicial]),
    });
  }
}
