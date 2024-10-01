import { Component, OnInit,  Input } from '@angular/core';

//#region  modelos
import { adquiriente } from '../../../models/Polizas/adquirienteP';
import { factura } from '../../../models/Polizas/detalleFactura';
import { poliza } from 'src/app/models/Polizas/polizas';
import { transaccion } from 'src/app/models/Polizas/transaccion';
import { Lista } from 'src/app/models/lista';

//#endregion modelos

//#region  servicios
import { PolizasService } from 'src/app/services/polizas.service';

//#endregion servicios

//#region bibliotecas
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { runInThisContext } from 'vm';
import { debug } from 'util';
import { selectInicial } from '../../../directives/validators/select.validator';
import { ErroresService } from '../../../services/errores.service';
//#endregion bibliotecas
@Component({
  selector: 'app-formulario-editar',
  templateUrl: './formulario-editar.component.html',

})
export class FormularioEditarComponent implements OnInit {

 @Input() numFac :string;
 @Input() numPoliza :string
 @Input() tipoMov:string
 @Input() tbl:string
 @Input() idtbl:number
 @Input() polizas :poliza [] = [];



  public porcentajes: Lista[];
  public datosTransaccion : transaccion ;
  public datosAdquiriente : adquiriente ;
  public datosFactura : factura ;
  public frmEditarAdquirientes: FormGroup;
  public resultado : string;


  public formularioTransaccion: boolean;
  public formularioAdquiriente: boolean;
  public formulariofactura : boolean
  public formulariouno : boolean
  public formulariodos : boolean
  public formulariotres : boolean

  constructor(
    public formBuilder: FormBuilder,
    private polizasService: ErroresService,
    private router : Router,
    public steps: NgbProgressbarConfig,
    private activedRoute : ActivatedRoute,


  ) {
    this.validacionesFormulario(),
    this.datosTransaccion = new transaccion(),
    this.datosAdquiriente = new adquiriente(),
    this.datosFactura = new factura(),
    this.formularioTransaccion = true
    this.formulariouno = true
    this.formularioAdquiriente = false
    this.formulariofactura = false


  }

  ngOnInit() {
    this.PorcentajeIva()
    this.busquedaPoliza()
    this.detalleTransaccion()
    this.TablaPolizas()
    this.tipoMov = ''

    }




  busquedaPoliza(){ // servcio que lista informacion del fromulario detalle polizas

    this.polizas.forEach(item =>{ // busqueda de los parametros coando vienen indefinidos
    item.numpoliza = this.numPoliza })
    this.tipoMov = String(this.activedRoute.snapshot.params.tipoMov)
    this.numFac = String(this.activedRoute.snapshot.params.numFac);
    this.numPoliza = String(this.activedRoute.snapshot.params.numPoliza);

    if (this.numFac === "undefined"){
      this.datosTransaccion = new transaccion()
    }else if (this.numFac != "undefined" && this.numPoliza != '')
    this.polizasService
        .transaccion(this.numFac,this.numPoliza)
        .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
          this.resultado = JSON.stringify(resp);
          this.datosTransaccion = resp.resultado
    });

  }

  detalleTransaccion(){
    this.formularioTransaccion = true
    this.formularioAdquiriente = false
   this.formulariofactura = false
    this.formulariouno = true
    this.formulariodos = false
    this.formulariotres = false
    this.busquedaPoliza()

  }

 detalleAdquiriente (){
   this.formularioTransaccion = false
   this.formularioAdquiriente = true
   this.formulariofactura = false
   this.formulariouno = false
   this.formulariodos = true
   this.formulariotres = false
   this.busquedaAdquiriente ()
 }

 detalleFactura(){
  this.formularioTransaccion = false
  this.formularioAdquiriente = false
  this.formulariofactura = true
  this.formulariouno = false
  this.formulariodos = false
  this.formulariotres = true
  this.busquedaFactura()
 }

 siguienteAdquiriente(){

   if(this.formularioTransaccion = true){
    this.formularioTransaccion = false
    this.formularioAdquiriente = true
    this.formulariofactura = false
    this.formulariouno = false
    this.formulariodos = true
    this.formulariotres = false
   this.busquedaAdquiriente ()
   }

 }
 siguienteFactura(){
  if(this.formularioAdquiriente = true){
    this.formularioTransaccion = false
    this.formularioAdquiriente = false
    this.formulariofactura = true
    this.formulariouno = false
    this.formulariodos = false
    this.formulariotres = true
    this.busquedaFactura()
   }
 }

 atrasAdquiriente(){
  if(this.formulariofactura = true){
    this.formularioTransaccion = false
    this.formularioAdquiriente = true
    this.formulariofactura = false
    this.formulariouno = false
    this.formulariodos = true
    this.formulariotres = false
    this.busquedaAdquiriente()
   }
 }
 atrasTransaccion(){
  if(this.formulariodos = true){
    this.formularioTransaccion = true
    this.formularioAdquiriente = false
    this.formulariofactura = false
    this.formulariouno = true
    this.formulariodos = false
    this.formulariotres = false
    this.busquedaPoliza()
   }

 }
 TablaPolizas() {

  this.polizasService
    .obtenerPolizas()
    .subscribe((resp: poliza) => {

      this.resultado = JSON.stringify(resp);
      this.polizas = resp.resultado;
  });


}
  busquedaAdquiriente(){// Servicio que lista la informacion del formulario detalle adquiriente

    this.tipoMov = String(this.activedRoute.snapshot.params.tipoMov)
    this.numPoliza = String(this.activedRoute.snapshot.params.numPoliza);

    if (this.tipoMov != '' && this.numPoliza != '' ){

      this.polizasService
          .adquiriente(this.numPoliza,this.tipoMov)
          .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
            this.resultado = JSON.stringify(resp);
            this.datosAdquiriente = resp.resultado
      });
    }
    this.busquedaFactura()

  }

  busquedaFactura(){ // Servicio lista la informacion del formulario detalle factura

    this.idtbl = Number(this.activedRoute.snapshot.params.idtbl);
    this.numPoliza = String(this.activedRoute.snapshot.params.numPoliza);
    this.tbl = String(this.activedRoute.snapshot.params.tbl);
    this.polizasService
        .factura(this.idtbl,this.numPoliza,this.tbl)
        .subscribe((resp: any) => {  // Solicitud completada con éxito o fallo
          this.datosFactura = resp.resultado
          if(this.datosFactura.descripcion === null){
            this.datosFactura.descripcion = ''
          }
    });
  }
  botonCancelar(){// Funcion de regreso a la pantalla home

    this.router.navigate(['/Errores/home'])

  }

  PorcentajeIva(){ // Servicio que lista informacion dentro del desplegable tasa iva
    this.polizasService
    .porcentajes()
    .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
      this.porcentajes = resp.resultado;
    });
}

 editarDatos(){    //Servicio de editar los datos de detalle factura

  this.datosFactura.valor
  this.datosFactura.valoriva
      this.polizasService
        .editarPoliza(this.datosFactura)
        .subscribe((res: any) => {// Solicitud completada con éxito o fallo

          Swal.fire({
            type: "success",
            title: "Operación Exitosa",
            text: res.resultado
          });
        }),
        (error: HttpErrorResponse) => { // Solicitud a fallado

        }

      this.router.navigate(['/Errores/home'])
 }




  validacionesFormulario() { // Validacion de los campos requeridos del formulario detalle factura
    this.frmEditarAdquirientes = this.formBuilder.group({
    linea: this.formBuilder.control('', [Validators.required]),
    valor: this.formBuilder.control(0, [Validators.required]),
    iva: this.formBuilder.control (0, [Validators.required]),
    tasa: this.formBuilder.control(0,[Validators.required,selectInicial]),
    SubIva: this.formBuilder.control(0, [Validators.required]),
    SubValor: this.formBuilder.control(0 ,[Validators.required]),
    total: this.formBuilder.control(0, [Validators.required]),

    });
  }
}
