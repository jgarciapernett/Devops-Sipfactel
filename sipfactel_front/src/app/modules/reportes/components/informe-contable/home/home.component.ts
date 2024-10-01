import { Component, OnInit, Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

//#region  servicios
import { ReportesService } from '../../../../../services/reportes.service';
//#endregion servicos

//#region  modelos
import { Lista } from 'src/app/models/lista';

import { repoContable } from 'src/app/models/repoContable';
//#endregion modelos

//#region bibliotecas
import { saveAs } from 'file-saver';
import { Subject } from 'rxjs';
import { NgbDateStruct, NgbDateParserFormatter, NgbDate, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { IdleConfigurationService } from 'src/app/services/idleConfiguration.service';
//#endregion bibliotecas

//#region formato de fechas
@Injectable()
export class CustomDateParserFormatter extends NgbDateParserFormatter {
  readonly DELIMITER = '/';

  parse(value: string): NgbDateStruct { // Se da la estructura principal de las fechas
    let result: NgbDateStruct = null;
    if (value) {
      const date = value.split(this.DELIMITER);
      result = {
        day: parseFloat(date[2]),
        month: parseFloat(date[2]),
        year: parseInt(date[2])
      };
    }
    return result;
  }

  format(date: NgbDateStruct): string { // Se da el formato visual de la fecha seleccionada en el datapicker
    let result: string = null;
    if (date) {
      result =
        date.day + this.DELIMITER + date.month + this.DELIMITER + date.year;
    }
    if (date >= date) {
      date = null
    }
    return result;
  }
}
//#endregion formato de fechas
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [
    // { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class HomeComponent implements OnInit {
  public opcionesFiltro$: Subject<repoContable[]> = new Subject<repoContable[]>();
  public contable: repoContable[] = [];
  public informe: repoContable
  public frmInforme: FormGroup;
  public fechaIni: NgbDate;
  public fechaFin: NgbDate;
  public fechaI: string;
  public fechaF: string;
  public nombre: string;
  public comp: string
  public Date: NgbDate;
  public tipoTransa : number;
  public transacciones : Lista[];
  public numTransa : string;
  public numPoliza : string;
 public resultado : string;
 public esCrear : boolean;
 public esEditar : boolean;
 public esConsultar : boolean;
 public tablainfo : boolean ;
 constructor(
   public formBuilder: FormBuilder,
   private calendar: NgbCalendar,
   public reportesService : ReportesService,
   private idl : IdleConfigurationService,


   ) {
  }

  ngOnInit() {

    this.ObtenerPermisos('informe');

    this.numTransa = ''
    this.numPoliza = ''
     this.tipoTransa = 0
     this.validacionesFiltro();
     this.Date = this.calendar.getToday();
     console.log("fecha date");
     console.log(this.calendar.getNext(this.Date, 'd', 1));

     this.listaTransacciones();



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
  obtenerReporte() { //Servicio que busca los registros en la tabla
    this.idl.timerIdle(1200)

    this.fechaF =`${this.fechaFin.day}/${this.fechaFin.month}/${this.fechaFin.year}`;
    this.fechaI =`${this.fechaIni.day}/${this.fechaIni.month}/${this.fechaIni.year}`;
    this.ValidacionDeParametros()
    this.reportesService
      .obtenerReporte(this.fechaF, this.fechaI, this.tipoTransa , this.numTransa , this.numPoliza , this.nombre)//parametros para la busqueda
      .subscribe(
        (res: any) => { // Solicitud con exitó o fallo

          this.resultado = JSON.stringify(res);
          if(res.codigoEstado === '200' && res.resultado.length === 0){
            this.tablainfo=false;
            Swal.fire({
              type: 'info',
              title: 'Atención',
              text: 'No Existe información asociada'
            });
          }else{
            this.tablainfo=true;
            this.contable = res.resultado;
            this.idl.timerIdle(180)
          }


        }
        );


  }


  descargarExcelInforme(opcion) { // Servicio que genera un archivo Excel de los registros

    this.ValidacionDeParametros()

      this.comp = opcion.compania

    this.fechaF =`${this.fechaFin.day}/${this.fechaFin.month}/${this.fechaFin.year}`,
    this.fechaI =`${this.fechaIni.day}/${this.fechaIni.month}/${this.fechaIni.year}`
    this.reportesService
        .descargarExcelInforme(this.fechaF, this.fechaI, this.comp, this.numTransa , this.numPoliza , this.tipoTransa , this.nombre) // parametros necesarios para la descarga
        .subscribe((res: Blob) => {// Solicitud con exitó o fallo
         saveAs(res, 'informe' + '.' + 'xlsx');
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: 'Archivo descargado correctamente'
          });
        });
      }

  listaTransacciones() { // lista  todas las compañias en un seleccionable

    this.reportesService.listatipoTransaccion().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.transacciones = resp.resultado;

    });
  }
  ValidacionDeParametros(){  // Validaciones para generar la busqueda dependiendo los datos filtrados


    if(this.fechaFin != undefined && this.fechaIni != undefined){
      this.nombre = 'fechas'
    }
     if(this.fechaFin != undefined && this.fechaIni != undefined &&  this.tipoTransa != 0 && this.numTransa != ''){
     this.nombre = 'transaccion'
   }
    if(this.fechaFin != undefined && this.fechaIni != undefined && this.tipoTransa != 0  ){
     this.nombre = 'tipoDoc'
   }
    if
   ( this.fechaFin != undefined && this.fechaIni != undefined && this.numTransa != '' ){
    this.nombre = 'Doc'
  }
  if
  ( this.fechaFin != undefined && this.fechaIni != undefined && this.numTransa != '' && this.numPoliza != '' ){
   this.nombre = 'DocumentosN'
 }
 if
  ( this.fechaFin != undefined && this.fechaIni != undefined && this.tipoTransa != 0 && this.numPoliza != '' ){
   this.nombre = 'transaPoliza'
 }
    if
  ( this.fechaFin != undefined && this.fechaIni != undefined && this.numPoliza != ''){
     this.nombre = 'numP'
   }
   if
   (this.fechaFin != undefined && this.fechaIni != undefined && this.tipoTransa != 0 && this.numTransa != '' &&  this.numPoliza != '')
  { this.nombre = 'todos'}
  }


  validacionesFiltro() { // Se validan los campos del filtro de busqueda que son obligatorios y su maximo de caracteres
    this.frmInforme = this.formBuilder.group({
      fechaIni: this.formBuilder.control('', [Validators.required]),
      fechaFin: this.formBuilder.control('', [Validators.required]),
      tptrans: this.formBuilder.control('', [Validators.required]),
      Ntransaccion: this.formBuilder.control('', [Validators.maxLength(50)]),
      poliza: this.formBuilder.control('', [Validators.maxLength(50)]),
    });
  }

  validacionFecha(): any{
     const fechaIni = this.frmInforme.get("fechaIni");
     if(fechaIni.value == undefined){
        return this.Date;
     }
     return this.calendar.getNext(NgbDate.from(fechaIni.value), 'd', 30);
  }


}
