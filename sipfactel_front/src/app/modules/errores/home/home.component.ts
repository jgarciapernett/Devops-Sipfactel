import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

//#region servicos
import { PolizasService } from 'src/app/services/polizas.service';

//#endregion rutas

//#region modelos

import { error } from 'src/app/models/Polizas/errorer';

//#endregion modelos

//#region  rutas
import { Router } from '@angular/router';

//#endregion rutas

//#region  bibliotecas
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { debug } from 'util';
import { ErroresService } from '../../../services/errores.service';
import { Categoria } from '../../../models/Polizas/categoria';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
//#endregion bibliotecas
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  public opcionesFiltro$: Subject<error[]> = new Subject<error[]>();
  public polizas: error[] = [];
  public guardaPolizas: error[] = [];


  public frmPolizas: FormGroup;
  public filtroTabla = new FormControl(" ");


  public numPoliza: string;
  public idtbl: number;
  public tbl: string;
  public tipoDoc: string
  public numFac: string;
  public nDebito: string;
  public tipoMov: string;
  public nCredito: string;
  public resultado: string;
  public cat: Categoria[] = []

  public transaccion: boolean;
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;
  tabla: boolean;
  categoria: number;


  constructor(
    public formGroup: FormBuilder,
    private polizaService: ErroresService,
    private spinner : Ng4LoadingSpinnerService,
    private router: Router,
    public formBuilder: FormBuilder

  ) {
    this.ObtenerPermisos('errores');
    this.inicializacion();
    this.tabla = true
    // this.busquedaCategoria()
    // this.filtroBusqueda()
    this.obtenerCategorias()
  }

  ngOnInit() {

    // this.validaciones();

    this.frmPolizas = this.formBuilder.group({
      Npoliza: this.formBuilder.control(this.numPoliza, [
      ]),
      Nfac: this.formBuilder.control(this.numFac, [
      ])
    });

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

  obtenerCategorias() {

    this.polizaService.categorias().subscribe((res: any) => {

      this.cat = res.resultado

    })
  }

  ObtenerPermisos(menuAlias: string) {


    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    objsesion.listRole.map(cat => {
      this.categoria = cat.categoria

    })
    menu.forEach(item => {

      if (item.menuAlias === menuAlias) {

        this.esCrear = item.crear;
        this.esConsultar = item.consultar;
        this.esEditar = item.modificar;
      }
    });


  }




  FormularioEditar(idtbl, poliza, tbl, tipoMov) { // El formulario editar


    this.numPoliza = poliza
    this.tbl = tbl
    this.idtbl = idtbl
    this.tipoMov = tipoMov

    this.router.navigate(['/Errores/editar', this.idtbl, this.numPoliza, this.tbl, this.tipoMov])

  }




  TablaPolizas() {

    this.polizaService
      .obtenerPolizas()
      .subscribe((resp: error) => {

        this.resultado = JSON.stringify(resp);
        this.polizas = resp.resultado;
      });


  }

  reenviarDatos() {


    Swal.fire({      // Mensaje de confirmar o cancelar la eliminacion de un registro

      title: '¿Esta seguro de reenviar esta factura?',
      type: 'question',
      showConfirmButton: true,
      confirmButtonColor: '#149275',
      cancelButtonColor: '#dc3545',
      showCancelButton: true,
    }).then(resp => {   // Validacion de eliminar Si se preciona el boton OK se consume el servicio y se elimina el registro
      if (resp.value) {


        this.polizas.forEach(item => {
          if (this.numPoliza == item.numpoliza) {
            this.numFac = item.numdocumento,
              this.tipoDoc = item.valor
          }
          else if (this.numFac == '') {
            this.numFac = item.numdocumento
            this.tipoDoc = item.valor
          }


        })
        this.polizaService.reenvio(this.numFac, this.tipoDoc).subscribe(
          (res: any) => {
            this.busquedaCategoria()

            Swal.fire({
              type: 'success',
              title: 'Operación Exitosa',
              text: res.resultado
            });
          },
          (error: HttpErrorResponse) => { },
        );
      }
      else if (resp.dismiss === Swal.DismissReason.cancel) { //Validacion del boton cancelar para que vuelva a mostrar la tabla
        this.busquedaCategoria()

      }
    })
  }

  remediarRegistro(errorid: number, iderror: number, idTabla: number, tabla: string) {

    this.polizaService.remediar(errorid, iderror, idTabla, tabla).subscribe((res: any) => {
      Swal.fire({
        type: 'success',
        title: 'Operación Exitosa',
        text: res.resultado
      });
    },
      (error: HttpErrorResponse) => { },
    );
    this.busquedaCategoria()

  }

  Recategorizar(Categoria: number, iderror: number, observaciones: string) {

    if (observaciones === '' || observaciones === null || observaciones === 'null') {
      Swal.fire({
        title: 'Por favor complete el campo observación',
        type: 'warning',
        showConfirmButton: true
      });
    } else {

      Swal.fire({      // Mensaje de confirmar o cancelar la eliminacion de un registro

        title: '¿Esta seguro de cambiar esta Categoria?',
        type: 'question',
        showConfirmButton: true,
        confirmButtonColor: '#149275',
        cancelButtonColor: '#dc3545',
        showCancelButton: true,
      }).then(resp => {   // Validacion de eliminar Si se preciona el boton OK se consume el servicio y se elimina el registro
        if (resp.value) {

          this.polizaService.categorizar(Categoria, iderror, observaciones).subscribe((res: any) => {
            Swal.fire({
              type: 'success',
              title: 'Operación Exitosa',
              text: res.resultado
            });
            this.busquedaCategoria()


          });

        }
        else if (resp.dismiss === Swal.DismissReason.cancel) { //Validacion del boton cancelar para que vuelva a mostrar la tabla
          this.busquedaCategoria()

        }
      });
    }
  }

  busquedaPoliza() {    // se servicio  busqueda por polizas

    if (this.numPoliza != '') {    // Validacion de que el campo de busqueda venga diligenciado
      this.polizaService
        .obtenerNumPolizas(this.categoria, this.numPoliza)
        .subscribe((resp: any) => {     // Solicitud completada con éxito o fallo
          this.resultado = JSON.stringify(resp);
          this.polizas = resp.resultado
        });
    }

  }


  filtroBusqueda() { // grupo de servicios para que se consuman segun la opcion diligenciada
     

    if (this.numFac == '' && this.numPoliza == '' && this.categoria != 0) {

      if(this.polizas.length === 0 && this.guardaPolizas.length === 0){
        this.busquedaCategoria()
      }else  if (this.guardaPolizas.length != 0){
        this.polizas = this.guardaPolizas
      }

    } else if (this.numFac == '' && this.numPoliza == '' && this.categoria != 0) {
      this.busquedaCategoria()
    } else if (this.numFac != '' && this.numPoliza != '' && this.categoria != 0) {
      this.busquedaParametros()
    }
    else if (this.numPoliza == '') {
      this.busquedaFactura()
    } else if (this.numPoliza != '') {

      this.busquedaPoliza()
    } else (
      this.busquedaCategoria()

    )

  }

  busquedaFactura() {// se servicio  busqueda por factura

    if (this.numFac != '') { // Validacion de que el campo de busqueda venga diligenciado
      this.polizaService
        .obtenerNumfacturas(this.categoria, this.numFac)
        .subscribe((resp: any) => {  // Solicitud completada con éxito o fallo
          this.resultado = JSON.stringify(resp);
          this.polizas = resp.resultado
        });
    }

  }
  busquedaCategoria() {// se servicio  busqueda por factura
  
    
    if (this.categoria != 0) { // Validacion de que el campo de busqueda venga diligenciado
      this.polizaService
      .obtenerCat(this.categoria)
      .subscribe((resp: any) => {  // Solicitud completada con éxito o fallo
        this.resultado = JSON.stringify(resp);
        this.polizas = resp.resultado
            
          this.guardaPolizas = this.polizas

         })
         ,(error:HttpErrorResponse) => {
          if(error.status){
            this.spinner.hide()
          }
        }
    }

  }
  busquedaParametros() {// se servicio  busqueda por factura

    if (this.categoria != 0 && this.numFac != '' && this.numPoliza != '') { // Validacion de que el campo de busqueda venga diligenciado
      this.polizaService
        .obtenerTodosParametros(this.categoria, this.numFac, this.numPoliza)
        .subscribe((resp: any) => {  // Solicitud completada con éxito o fallo
          this.resultado = JSON.stringify(resp);
          this.polizas = resp.resultado
        });
    }

  }
  obtenerTodos() {

    this.polizaService.obtenerErrores().subscribe((res: any) => {
      this.resultado = JSON.stringify(res);
      this.polizas = res.resultado

    })
  }
  Todos() {

    this.polizaService.obtenerErrores().subscribe((res: any) => {
      this.polizas = res.resultado
    })
  }


  validaciones() {  // validacion de los campos del filtro para que se inicialicen en vacio
    this.frmPolizas = this.formGroup.group({
      Npoliza: this.formGroup.control('',),
      Nfac: this.formGroup.control('',),
      NotaC: this.formGroup.control('',),
      NotaD: this.formGroup.control('',)
    });
  }
}
