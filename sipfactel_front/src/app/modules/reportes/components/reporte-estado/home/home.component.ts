import { Component, OnInit, Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReportesService } from 'src/app/services/reportes.service';
import { Lista } from 'src/app/models/lista';
import { repoEstado } from '../../../../../models/repoEstado';
import { saveAs } from 'file-saver';

//#region bibliotecas
import { Subject } from 'rxjs';
import { NgbDateAdapter, NgbDateStruct, NgbDateParserFormatter, NgbDate, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { sucursal } from '../../../../../models/desplegableSucu';
import Swal from 'sweetalert2';
import { IdleConfigurationService } from '../../../../../services/idleConfiguration.service';
import { HttpErrorResponse } from '@angular/common/http';
import { JwtService } from '../../../../../services/jwt.service';
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
      date = null;
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
  public frmReporte: FormGroup;
  public opcionesFiltro$: Subject<repoEstado[]> = new Subject<repoEstado[]>();
  public Reporte: repoEstado[] = [];
  public companias: Lista[];
  public sucursales: sucursal[];
  public estados: Lista[];
  public companiaSe: string;
  public numPoliza: string;
  public numTransa: string;
  public sucursal: string;
  public estado: number;
  public resultado: string;
  public fechaIni: NgbDate;
  public fechaFin: NgbDate;
  public fechaI: string;
  public fechaF: string;
  public Date: NgbDate;
  public nombre: string;
  public botonExcel: boolean = true;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;

  constructor(
    public formBuilder: FormBuilder,
    private calendar: NgbCalendar,
    public reportesService: ReportesService,
    private idl : IdleConfigurationService,


  ) {
 this.ObtenerPermisos('reporte')

  }

  ngOnInit() {
    this.Date = this.calendar.getToday();
    this.numTransa = ''
    this.numPoliza = ''
    this.validacionesParametros();
    this.listaEstafoFactura();
    this.listaCompañia();
    this.listaSucursales();

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

  listaCompañia() { // lista  todas las compañias en un seleccionable
    // tslint:disable-next-line: deprecation
    this.reportesService.listaCompañinas().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.companias = resp.resultado;

    });
  }
  listaSucursales() { // lista  todas las compañias en un seleccionable
    // tslint:disable-next-line: deprecation
    this.reportesService.listaSucursales().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.sucursales = resp.resultado;

    });
  }
  listaEstafoFactura() { // lista  todas las compañias en un seleccionable
    // tslint:disable-next-line: deprecation
    this.reportesService.listaEstado().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.estados = resp.resultado;

    });
  }

  validacionesCampos() {
    if (this.fechaI != '' && this.fechaF !== '' && this.estado != 0) {
      this.nombre = 'obligatorio';

    }
    if (this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.sucursal != undefined) {
      this.nombre = 'sucursal'

    }
    if (this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.companiaSe != undefined) {
      this.nombre = 'compania';

    }
    if (this.fechaI != undefined && this.fechaF != undefined && this.estado != 0 && this.numPoliza != '') {
      this.nombre = 'poliza';

    }
    if (this.fechaI != undefined && this.fechaF != undefined && this.estado !== 0
      && this.numTransa != '') {
      this.nombre = 'numTransa';

    }
    if (
      this.fechaI != undefined && this.fechaF !== undefined && this.estado !== 0
      && this.numPoliza != '' && this.numTransa != '') {
      this.nombre = 'poliza&numTransa';
    }
    if (
      this.fechaI != undefined && this.fechaF !== undefined && this.estado !== 0
      && this.numPoliza != '' && this.companiaSe != undefined) {
      this.nombre = 'poliza&compania';
    }
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado !== 0
      && this.numPoliza != '' && this.sucursal != undefined) {
      this.nombre = 'poliza&sucursal';

    }
    
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numPoliza != '' && this.numTransa != '' && this.companiaSe != undefined) {
      this.nombre = 'poliza&transaccion&compania';

    }
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numPoliza != '' && this.numTransa != '' && this.companiaSe != undefined) {
      this.nombre = 'poliza&transaccion&compania';

    }
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numPoliza != '' && this.sucursal != undefined && this.companiaSe != undefined) {
      this.nombre = 'poliza&sucursal&compania';

    }
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numPoliza == '' && this.sucursal != undefined && this.companiaSe != undefined && this.numTransa === '') {
      this.nombre = 'compania&sucursal';

    }
    if (this.fechaI != undefined && this.fechaF != undefined && this.estado !== 0
      && this.companiaSe != '' && this.numPoliza != '' &&
      this.numTransa != '' && this.sucursal != undefined) {
      this.nombre = 'todosCampos';

    }
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numTransa != '' && this.companiaSe != undefined && this.numPoliza == '') {
      this.nombre = 'numTransa&compania';
    }
    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numTransa != '' && this.sucursal != undefined && this.companiaSe == undefined) {
      this.nombre = 'numTransa&sucursal';
    }

    if (
      this.fechaI != undefined && this.fechaF != undefined && this.estado != 0
      && this.numTransa != '' && this.companiaSe != undefined && this.sucursal != undefined && this.numPoliza == '') {
      this.nombre = 'numTransa&compania&sucursal';
      // }


    }
  }

  obtenerReporte() { // Servicio que busca los registros en la tabla
    
    this.idl.timerIdle(1200)
    this.fechaF = `${this.fechaFin.day}/${this.fechaFin.month}/${this.fechaFin.year}`,
    this.fechaI = `${this.fechaIni.day}/${this.fechaIni.month}/${this.fechaIni.year}`;
    this.validacionesCampos()

    this.reportesService
      // tslint:disable-next-line: max-line-length
      .obtenerReporteEstado(this.companiaSe, this.estado, this.fechaF, this.fechaI, this.numTransa, this.numPoliza, this.sucursal, this.nombre)// parametros para la busqueda
      .subscribe(
        (res: any) => { // Solicitud con exitó o fallo

          this.resultado = JSON.stringify(res);
          this.Reporte = res.resultado;
          if (this.Reporte.length !== 0) {
            this.resultado = JSON.stringify(res);
            this.Reporte = res.resultado;
            this.botonExcel = false;
            this.idl.timerIdle(180)
          }
          else if (this.Reporte.length == 0) {
            Swal.fire({
              type: 'info',
              title: 'Atención',
              text: 'No existe información asociada',
              footer: ''
            });

            this.botonExcel = true;
          }
          
          
        });
        };

  


  descargarExcelReporte() { // Servicio que genera un archivo Excel de los registros

    this.validacionesCampos()
    this.reportesService
      .descargarExcelReporte(this.companiaSe, this.estado, this.fechaF, this.fechaI, this.numTransa, this.numPoliza, this.sucursal, this.nombre) // parametros necesarios para la descarga
      .subscribe((res: Blob) => { // Solicitud con exitó o fallo
        saveAs(res, 'informe' + '.' + 'xlsx');
        Swal.fire({
          type: 'success',
          title: 'Operación Exitosa',
          text: 'Archivo descargado correctamente'
        });
      });
  }


  validacionesParametros() {
    this.frmReporte = this.formBuilder.group({
      fechaIni: this.formBuilder.control('', [Validators.required]),
      fechaFin: this.formBuilder.control('', [Validators.required]),
      poliza: this.formBuilder.control(null, [Validators.maxLength(40)]),
      Ntransaccion: this.formBuilder.control('', [Validators.maxLength(40)]),
      Estadotrans: this.formBuilder.control('', [Validators.required]),
      companias: this.formBuilder.control(0),
      sucursal: this.formBuilder.control(0),
    });
  }

}
