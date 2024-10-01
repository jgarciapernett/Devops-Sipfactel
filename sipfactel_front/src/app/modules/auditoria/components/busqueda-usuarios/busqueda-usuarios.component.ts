import { Component, OnInit, Injectable } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';

//#region servicios
import { AuditoriaService } from 'src/app/services/auditoria.service';
import { UtilidadesService } from 'src/app/services/utilidades.service';
//#endregion servicios

//#region modelos
import { auditoria } from 'src/app/models/auditoriaUsuario';
//#endregion modelos

//#region bibliotecas
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { saveAs } from 'file-saver';
import { HttpErrorResponse } from '@angular/common/http';

import {
  NgbCalendar,
  NgbDateParserFormatter,
  NgbDate
} from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { PaginacionService } from 'src/app/services/paginacion.service';
//#endregion bibliotecas



//#region formatos de fechas

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
        // tslint:disable-next-line: radix
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
  selector: 'app-busqueda-usuarios',
  templateUrl: './busqueda-usuarios.component.html',
  styleUrls: ['./busqueda-usuarios.component.css'],

  providers: [
    // { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class BusquedaUsuariosComponent implements OnInit {
  public tablaBusqueda: boolean;
  public fechaNoAdmitida: boolean;
  public desactivado: boolean;
  public opcionesFiltro$: Subject<auditoria[]> = new Subject<auditoria[]>();
  public resultado: string;
  public auditorias: auditoria[] = [];
  public filtroTabla = new FormControl('');
  public fechaIni: NgbDate;
  public fechaFin: NgbDate;
  public fechaI: string;
  public fechaF: string;
  public usuario: string;
  public Date: NgbDate;
  public frmDatosProveedor: FormGroup;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;


  constructor(
    private calendar: NgbCalendar,
    private auditorService: AuditoriaService,
    private utilidadesService: UtilidadesService,
    public formBuilder: FormBuilder,


  ) {
    this.ObtenerPermisos('auditoria');
    
  }
  
  ngOnInit() {
    this.validacionesParametros()
    this.Date = this.calendar.getToday();


  }

 
  ObtenerPermisos(menuAlias: string) {// Servicio que busca los permisos del usuari para este modulo
    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    menu.forEach(item => {
      if (item.menuAlias === menuAlias) { // mapea los permisos del usuario

        this.esCrear = item.crear;
        this.esConsultar = item.consultar;
        this.esEditar = item.modificar;
      }
    });
  }

  obtenerDatosUsuario() { //Servicio que busca los registros en la tabla
    this.desactivado = true;
   
  this.validacionFechas()
  
  this.auditorService
      .obtenerPorFechaYUsuario(this.fechaF, this.fechaI, this.usuario)//parametros para la busqueda
      .subscribe(
        (res: any) => { // Solicitud con exitó o fallo
          this.auditorias = res.resultado;
          
          if (this.auditorias.length === 0) {
            this.desactivado = false;
          }
          if (!this.auditorias) {
            this.desactivado = false;
          }
          this.tablaBusqueda = true;
          this.resultado = JSON.stringify(res);
          
        },
        (error: HttpErrorResponse) =>
          (this.tablaBusqueda = false, (this.desactivado = false))
      );

  }

  descargarExcel() { // Servicio que genera un archivo Excel de los registros
      
  this.validacionFechas()
  
    
  this.auditorService
      .descargarExcelAuditoria(this.fechaF, this.fechaI, this.usuario) // parametros necesarios para la descarga
      .subscribe((res: any) => { // Solicitud con exitó o fallo
        saveAs(res, 'Auditoria' + '.' + 'xlsx');
        Swal.fire({
          type: 'success',
          title: 'Operación Exitosa',
          text: 'Archivo descargado correctamente'
        });
      });
    }

    validacionFechas(){ // validaciones de las fechas


      if( // Cambia todos los parametros a null
        this.fechaFin === undefined &&
        this.fechaIni === undefined &&
        this.usuario === undefined
      ){
        this.fechaF = null;
        this.fechaI = null;
        this.usuario = null;
  
      }else if (this.fechaFin == null && this.fechaIni == null && this.usuario != '') { // Cambia los parametros de fecha a null
        this.fechaF = null;
        this.fechaI = null; 
      }
      
      else if( this.fechaFin != null && this.fechaIni != null && this.usuario == undefined || this.usuario == ''){  // cambia el parametro usuario a null
        this.fechaF =`${this.fechaFin.day}/${this.fechaFin.month}/${this.fechaFin.year}`,
        this.fechaI =`${this.fechaIni.day}/${this.fechaIni.month}/${this.fechaIni.year}`
        this.usuario = null

      } else if( this.fechaFin != null && this.fechaIni != null && this.usuario != undefined || this.usuario != ''){
        this.fechaF =`${this.fechaFin.day}/${this.fechaFin.month}/${this.fechaFin.year}`,
        this.fechaI =`${this.fechaIni.day}/${this.fechaIni.month}/${this.fechaIni.year}`
      }
      else
    {
      this.fechaF =`${this.fechaFin.day}/${this.fechaFin.month}/${this.fechaFin.year}`,
      this.fechaI =`${this.fechaIni.day}/${this.fechaIni.month}/${this.fechaIni.year}`
    }
      
  
    }
    validacionesParametros() { // Validacion de los campos del filtro de busqueda

    this.frmDatosProveedor = this.formBuilder.group({
      fechaIni: this.formBuilder.control('',[Validators.required]),
      fechaFin: this.formBuilder.control('',[Validators.required]),
      usuario: this.formBuilder.control(this.usuario),
     

      
    });
  }

}
