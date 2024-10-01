import { Component, OnInit, EventEmitter } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ResolucionesService } from 'src/app/services/resoluciones.service';
import { ResolucionFacturas } from '../../../models/resoluciones/ResolucionFacturas';
import { Resultado } from '../../../models/resultado';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-modal-crea-edita-resolucion-factura',
  templateUrl: './modal-crea-edita-resolucion-factura.component.html',
  styleUrls: ['./modal-crea-edita-resolucion-factura.component.css']
})
export class ModalCreaEditaResolucionFacturaComponent implements OnInit {

  public initialState: any;
  public event = new EventEmitter();
  public encabezadoModal: String = "";
  public formularioResolucion: FormGroup;
  private factura: ResolucionFacturas;
  private facturas: ResolucionFacturas[];
  private accion: String;
  public soloLecturaNumeroInicial: boolean = false;
  public soloLecturaPrefijo: boolean = false;
  public soloLecturaFechaInicial: boolean = false;
  public visualizarNumeroResolucion: boolean = true;
  public fechaMinima: String;

  constructor(
    public bsModalRef: BsModalRef,
    public formBuilder: FormBuilder,
    public service: ResolucionesService,
    protected modalService: BsModalService
  ) { }

  ngOnInit() {
    this.initialState = this.modalService.config.initialState;
    this.encabezadoModal = this.initialState.titulo;
    this.factura = this.initialState.factura;
    this.facturas = this.initialState.facturas;
    this.accion = this.initialState.accion;
    this.armarFormulario(this.factura);
    this.validarFechaMinimaInicial();
  }

  /**
   * Metodo que valida la fecha minima para registrar la resolucion
   */

  private validarFechaMinimaInicial() {
    let listaFechas = [];
    let fechaMaxima: Date = new Date();
    if(this.accion == "editar"){
      let buscarRegistroAnterior: number = this.facturas.indexOf(this.factura);
      if(buscarRegistroAnterior != 0){
        let data: ResolucionFacturas = this.facturas[buscarRegistroAnterior - 1];
        fechaMaxima = new Date(data.fechaFinal);
        fechaMaxima.setDate(fechaMaxima.getDate() + 1);
      }
    } else {
      for (let fechas of this.facturas) {
        listaFechas.push(new Date(fechas.fechaFinal));
      }
      if(listaFechas.length > 0){
        fechaMaxima = new Date(Math.max(...listaFechas));
        fechaMaxima.setDate(fechaMaxima.getDate() + 1);
      }
    }
    this.fechaMinima = `${fechaMaxima.getFullYear()}-${this.agregarZeroIzquierda(fechaMaxima.getMonth() + 1)}-${this.agregarZeroIzquierda(fechaMaxima.getDate())}`;
  }

  private agregarZeroIzquierda(campo: number): String {
    let modificado: String;
    if (campo <= 9) {
      modificado = `0${campo}`;
    } else {
      modificado = String(campo);
    }
    return modificado;
  }

  /**
   * Metodo que crea los componentes del formulario
   * @Param notaRecibida, NotasDebitoCredito => objeto con la informacion basica de la nota a crear o editar
   */

  private armarFormulario(facturaRecibida: ResolucionFacturas) {
    this.formularioResolucion = this.formBuilder.group({
      'llave': [facturaRecibida.llave],
      'compania': [facturaRecibida.compania, [Validators.required]],
      'sucursal': [facturaRecibida.sucursal, [Validators.required]],
      'trTipoId': [facturaRecibida.trTipoId, [Validators.required]],
      'prefijo': [facturaRecibida.prefijo, [Validators.required]],
      'numeroResolucion': [facturaRecibida.numeroResolucion, [Validators.required]],
      'fechaInicial': [facturaRecibida.fechaInicial, [Validators.required]],
      'fechaFinal': [facturaRecibida.fechaFinal, [Validators.required]],
      'numeroInicial': [facturaRecibida.numeroInicial, [Validators.required]],
      'numeroFinal': [facturaRecibida.numeroFinal, [Validators.required]],
      'contador': [facturaRecibida.contador],
      'producto': [facturaRecibida.producto],
      'estado': [facturaRecibida.estado],
    });
    this.validarDisableds();
  }

  /**
   * Metodo que valida si algunos campos se debe inactivar
   */
  private validarDisableds() {
    if (this.accion == "editar") {
      this.visualizarNumeroResolucion = false;
      this.soloLecturaPrefijo = true;
      let buscarPrefijo: ResolucionFacturas[] = this.facturas.filter(data => data.prefijo == this.factura.prefijo);
      if (buscarPrefijo.length > 1 || this.factura.contador > 0) {
        this.soloLecturaNumeroInicial = true;
      }
      if(this.factura.estado == 'TRUE'){
        this.soloLecturaFechaInicial = true;
      }
    }
  }

  /**
   * Metodo que guarda la resolucion y define si es una edicion o creacion
   */

  public guardarResolucion() {
    if (this.formularioResolucion.valid) {
      let facturaEnviar: ResolucionFacturas = this.armarObjetoFacturas();
      if (facturaEnviar.numeroFinal <= facturaEnviar.numeroInicial) {
        Swal.fire({
          type: "warning",
          title: "Ha ocurrido un error",
          text: "Los rangos de la numeración no son correctos"
        });
      } else if (facturaEnviar.numeroFinal <= 0 || facturaEnviar.numeroInicial <= 0) {
        Swal.fire({
          type: "warning",
          title: "Ha ocurrido un error",
          text: "El rango no puede iniciar en 0"
        });
      } else if(new Date(facturaEnviar.fechaInicial) > new Date(facturaEnviar.fechaFinal)) {
        Swal.fire({
          type: "warning",
          title: "Ha ocurrido un error",
          text: "Los rangos de fechas no concuerdan"
        });
      } else {
        if (this.accion == "crear") {
          this.crearResolucion(facturaEnviar);
        } else {
          this.editarResolucion(facturaEnviar);
        }
      }
    } else {
      Swal.fire({
        type: "warning",
        title: "Ha ocurrido un error",
        text: "Formulario incorrecto"
      });
    }
  }

  public editarResolucion(facturaEnviar: ResolucionFacturas): void {
    this.service.editarResolucionFacturas(facturaEnviar).subscribe(
      (resp: Resultado) => {
        if (resp.succes) {
          Swal.fire("Trabajo satisfactorio", resp.message, "success");
          this.event.emit(true);
          this.bsModalRef.hide();
        } else {
          Swal.fire("Ha ocurrido un error", resp.message, "warning");
        }
      }
    );
  }

  /**
   * Metodo que arma el objeto para enviar al servicio
   */

  private armarObjetoFacturas(): ResolucionFacturas {
    let factura = new ResolucionFacturas();
    factura.llave = this.factura.llave;
    factura.sucursal = this.factura.sucursal;
    factura.compania = this.factura.compania;
    factura.producto = this.factura.producto;
    factura.estado = this.factura.estado;
    factura.contador = this.factura.contador;
    factura.numeroInicial = Number(this.formularioResolucion.value.numeroInicial);
    factura.numeroFinal = Number(this.formularioResolucion.value.numeroFinal);
    factura.prefijo = this.formularioResolucion.value.prefijo;
    factura.numeroResolucion = Number(this.formularioResolucion.value.numeroResolucion);
    factura.fechaInicial = String(this.formularioResolucion.value.fechaInicial).replace("-", '/').replace("-", "/");
    factura.fechaFinal = String(this.formularioResolucion.value.fechaFinal).replace("-", "/").replace("-", "/");
    factura.trTipoId = Number(this.formularioResolucion.value.trTipoId);
    factura.vigencia = this.factura.vigencia;
    return factura;
  }

  /**
  * Metodo que valida si el prefijo de la resolucion debe seguir un consecutivo
  */

  public validarConsecutivoPrefijo() {
    if (this.accion != "editar") {
      if (this.formularioResolucion.value.prefijo) {
        let buscarPrefijo: ResolucionFacturas[] = this.facturas.filter(data => data.prefijo == this.formularioResolucion.value.prefijo);
        if (buscarPrefijo.length > 0) {
          let numerosResolucion: number[] = [];
          for (let numeracionFinal of buscarPrefijo) {
            numerosResolucion.push(numeracionFinal.numeroFinal);
          }
          let numeroMayor: number = Math.max(...numerosResolucion);

          this.formularioResolucion.patchValue({
            numeroInicial: numeroMayor + 1
          });
          this.soloLecturaNumeroInicial = true;
        } else {
          this.soloLecturaNumeroInicial = false;
          this.formularioResolucion.patchValue({
            numeroInicial: 1
          });
        }

      }
    }
  }

  /**
   * Metodo que consume el servicio para crear resolucion
   */

  public crearResolucion(facturaEnviar: ResolucionFacturas): void {
    this.service.crearResolucionFacturas(facturaEnviar).subscribe(
      (resp: Resultado) => {
        if (resp.succes) {
          Swal.fire("Trabajo satisfactorio", resp.message, "success");
          this.event.emit(true);
          this.bsModalRef.hide();
        } else {
          Swal.fire("Ha ocurrido un error", resp.message, "warning");
        }
      }
    );
  }

}
