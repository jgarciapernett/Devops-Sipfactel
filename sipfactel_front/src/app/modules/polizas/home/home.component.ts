import { Component, OnInit, Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { NgbDateStruct, NgbDateParserFormatter, NgbDate, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';

//#endregion rutas

//#region modelos

import { poliza } from 'src/app/models/Polizas/polizas';

//#endregion modelos

//#region  rutas
import { Router } from '@angular/router';

//#endregion rutas

//#region  bibliotecas
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { PolizasService } from '../../../services/polizas.service';
import { Categoria } from 'src/app/models/Polizas/categoria';

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

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  providers: [
    // { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class HomeComponent implements OnInit {
  public polizas : poliza[] = [];


  public frmPolizas: FormGroup;
  public filtroTabla = new FormControl(" ");


  public numPoliza:string;
  public tipoDoc : string
  public numFac:string;
  public fechaIniEnvio: NgbDate;
  public fechaFinEnvio: NgbDate;
  public fechaIniEmision: NgbDate;
  public fechaFinEmision: NgbDate;
  public nDebito:string;
  public tipoMov:string;
  public nCredito:string;
  public resultado: string;

  public transaccion : boolean;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;
  private botonFiltrar: boolean = false;

  public Date: NgbDate;

  //paginacion
  public totalElements: number = 0;
  public pagina: number = 1;
  public maxSize: number = 5;
  public pageSize: number = 10;

  constructor(
    public formGroup: FormBuilder,
    private polizaService: PolizasService,
    private router: Router,
    private calendar: NgbCalendar
    ) {
    this.ObtenerPermisos('polizas');
    this.inicializacion();
    this.validaciones();
  }

  ngOnInit() {
    this.Date = this.calendar.getToday();
    this.getPolizas(null, null, null, null);
  }

  inicializacion() {
    this.transaccion = false;
    this.tipoMov = ''
    this.numFac = '';
    this.tipoDoc = '';
    this.numPoliza = '';
    this.nDebito = '';
    this.nCredito = '';
  }


  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que tenga
    const objsesion = JSON.parse(localStorage.getItem('sesion'));
    const menu = objsesion.listMenu;
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

  formularioEditar(dniDocumento: number,tipo: string, adquiriente: number) { // El formulario editar
   this.router.navigate(['/Polizas/editar', dniDocumento, tipo, adquiriente])
  }

  reenviarDatos(numpol , numDoc){
    Swal.fire ( {      // Mensaje de confirmar o cancelar la eliminacion de un registro

      title: '¿Esta seguro de reenviar esta factura?',
      type: 'question',
      showConfirmButton: true,
      confirmButtonColor: '#149275',
      cancelButtonColor: '#dc3545',
      showCancelButton : true,
    } ).then(resp =>{   // Validacion de eliminar Si se preciona el boton OK se consume el servicio y se elimina el registro
      if (resp.value)
      {

        this.polizaService.reenvio(numpol,numDoc).subscribe(
          (res:any) => {
            this.validarFiltrosPoliza(false)
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: res.resultado
          });
        },
        (error:HttpErrorResponse) => {},
        );
        }
      else if (resp.dismiss === Swal.DismissReason.cancel) { //Validacion del boton cancelar para que vuelva a mostrar la tabla
        this.validarFiltrosPoliza(false);
      }
    })
  }

   validarFiltrosPoliza(boton:boolean){    // se servicio  busqueda por polizas
    this.botonFiltrar=boton;

    let fIniEmision = this.frmPolizas.get('fIniEmision').value;
    let fFinEmision = this.frmPolizas.get('fFinEmision').value;
    let fIniEnvio = this.frmPolizas.get('fIniEnvio').value;
    let fFinEnvio = this.frmPolizas.get('fFinEnvio').value;

    fIniEmision = fIniEmision ? `${fIniEmision.day}/${fIniEmision.month}/${fIniEmision.year}` : null;
    fFinEmision = fFinEmision ? `${fFinEmision.day}/${fFinEmision.month}/${fFinEmision.year}` : null;
    fIniEnvio = fIniEnvio ? `${fIniEnvio.day}/${fIniEnvio.month}/${fIniEnvio.year}` : null;
    fFinEnvio = fFinEnvio ? `${fFinEnvio.day}/${fFinEnvio.month}/${fFinEnvio.year}` : null;

    /*if(fIniEmision == null  && fFinEmision == null
      && fIniEnvio == null  && fFinEnvio == null
      && (this.numPoliza == null || this.numPoliza == "")
      && (this.numFac == null || this.numFac == "")){
        Swal.fire({
          type: 'info',
          title: 'Atención',
          text: 'Diligencie algun filtro'
        });
        return;
    }*/
    this.getPolizas(fIniEnvio, fFinEnvio, fIniEmision, fFinEmision);
    this.botonFiltrar=false;
  }

  getPolizas(fIniEnvio: string, fFinEnvio: string,
    fIniEmision: string, fFinEmision: string): void{
    this.polizaService
    .obtenerPolizas(this.numPoliza, this.numFac, fIniEnvio, fFinEnvio, fIniEmision, fFinEmision, this.botonFiltrar ? this.pagina=1 : this.pagina, this.pageSize)
    .subscribe((resp: any) => {     // Solicitud completada con éxito o fallo
      this.resultado = JSON.stringify(resp);
      if(resp.codigoEstado === '200' && resp.resultado.data.length === 0){
        Swal.fire({
          type: 'info',
          title: 'Atención',
          text: 'No Existe información asociada'
        });
        this.polizas = [];
        this.totalElements = 0;
      }else{
        this.polizas = resp.resultado.data;
        this.totalElements = resp.resultado.totalElements;
      }
});
  }

  validaciones() {  // validacion de los campos del filtro para que se inicialicen en vacio
    this.frmPolizas = this.formGroup.group({
      fIniEnvio: this.formGroup.control('',),
      fFinEnvio: this.formGroup.control('',),
      fIniEmision: this.formGroup.control('',),
      fFinEmision: this.formGroup.control('',),
      Npoliza: this.formGroup.control('',),
      Nfac: this.formGroup.control('', ),
      NotaC: this.formGroup.control('',),
      NotaD: this.formGroup.control('',)
    });
  }

  validacionFiltro(): boolean{
     if(this.frmPolizas.get('fIniEnvio').errors || this.frmPolizas.get('fFinEnvio').errors
     || this.frmPolizas.get('fIniEmision').errors || this.frmPolizas.get('fFinEmision').errors){
       return true;
     }
     if((this.frmPolizas.get('fIniEnvio').value && !this.frmPolizas.get('fFinEnvio').value)
     || (!this.frmPolizas.get('fIniEnvio').value && this.frmPolizas.get('fFinEnvio').value)){
       return true;
     }
     if((this.frmPolizas.get('fIniEmision').value && !this.frmPolizas.get('fFinEmision').value)
     || (!this.frmPolizas.get('fIniEmision').value && this.frmPolizas.get('fFinEmision').value)){
       return true;
     }
     return false;
  }

  cambiarPagina(e: any): void{
    this.pagina = e;
    if(!this.botonFiltrar){
    this.validarFiltrosPoliza(false);
    }
  }

  cambiarTamano(): void{
    this.validarFiltrosPoliza(false);
  }

}
