import { Component, OnInit, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { NotasDebitoCredito } from '../../../models/resoluciones/notasDebitoCredito';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ResolucionesNotasService } from 'src/app/services/resolucionesNotas.service';
import { Resultado } from '../../../models/resultado';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-modal-crea-edita',
  templateUrl: './modal-crea-edita.component.html',
  styleUrls: ['./modal-crea-edita.component.css']
})
export class ModalCreaEditaComponent implements OnInit {

  public initialState: any;
  public event = new EventEmitter();
  public encabezadoModal: String = "";
  public formularioResolucion: FormGroup;
  private nota: NotasDebitoCredito;
  private notas: NotasDebitoCredito[];
  private accion: String;
  public soloLecturaNumeroInicial: boolean = false;
  public soloLecturaPrefijo: boolean = false;

  constructor(
    public bsModalRef: BsModalRef,
    public formBuilder: FormBuilder,
    public service: ResolucionesNotasService,
    private router: Router,
    protected modalService: BsModalService
  ) { }

  ngOnInit() {
    this.initialState = this.modalService.config.initialState;
    this.encabezadoModal = this.initialState.titulo;
    this.nota = this.initialState.nota;
    this.notas = this.initialState.notas;
    this.accion = this.initialState.accion;
    this.armarFormulario(this.nota);
  }

  /**
   * Metodo que crea lso componentes del formulario
   * @Param notaRecibida, NotasDebitoCredito => objeto con la informacion basica de la nota a crear o editar
   */

  private armarFormulario(notaRecibida: NotasDebitoCredito) {
    this.formularioResolucion = this.formBuilder.group({
      'llave': [notaRecibida.llave],
      'compania': [notaRecibida.compania, [Validators.required]],
      'sucursal': [notaRecibida.sucursal, [Validators.required]],
      'trTipoId': [notaRecibida.trTipoId, [Validators.required]],
      'prefijo': [notaRecibida.prefijo, [Validators.required]],
      'numeroInicial': [notaRecibida.numeroInicial, [Validators.required]],
      'numeroFinal': [notaRecibida.numeroFinal, [Validators.required]],
      'tipoNota': [notaRecibida.tipoNota],
      'contador': [notaRecibida.contador],
      'producto': [notaRecibida.producto],
      'estado': [notaRecibida.estado],
      'auditoria': [notaRecibida.auditoria]
    });
    this.validarDisableds();
  }

  /**
   * Metodo que valida si algunos campos se debe inactivar
   */
  private validarDisableds() {
    if (this.accion == "editar") {
      this.soloLecturaPrefijo = true;
      let buscarPrefijo: NotasDebitoCredito[] = this.notas.filter(data => data.prefijo == this.nota.prefijo);
      if(buscarPrefijo.length > 1 || this.nota.contador > 0){
        this.soloLecturaNumeroInicial = true;
      }
    }
  }

  /**
   * Metodo que guarda la resolucion y define si es una edicion o creacion
   */

  public guardarResolucion() {
    if (this.formularioResolucion.valid) {
      let notaEnviar: NotasDebitoCredito = this.armarObjetoNotas();
      if (notaEnviar.numeroFinal <= notaEnviar.numeroInicial) {
        Swal.fire({
          type: "warning",
          title: "Ha ocurrido un error",
          text: "Los rangos de la numeración no son correctos"
        });
      } else if (notaEnviar.numeroFinal <= 0 || notaEnviar.numeroInicial <= 0) {
        Swal.fire({
          type: "warning",
          title: "Ha ocurrido un error",
          text: "El rango no puede iniciar en 0"
        });
      } else {
        if (this.accion == "crear") {
          this.crearResolucion(notaEnviar);
        } else {
          this.editarResolucion(notaEnviar);
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

  /**
   * Metodo que arma el objeto para enviar al servicio
   */

  private armarObjetoNotas(): NotasDebitoCredito {
    let notaDebitoCredito = new NotasDebitoCredito();
    notaDebitoCredito.llave = this.nota.llave;
    notaDebitoCredito.sucursal = this.nota.sucursal;
    notaDebitoCredito.compania = this.nota.compania;
    notaDebitoCredito.producto = this.nota.producto;
    notaDebitoCredito.estado = this.nota.estado;
    notaDebitoCredito.auditoria = this.nota.auditoria;
    notaDebitoCredito.contador = this.nota.contador;
    notaDebitoCredito.tipoNota = this.nota.tipoNota;
    notaDebitoCredito.numeroInicial = Number(this.formularioResolucion.value.numeroInicial);
    notaDebitoCredito.numeroFinal = Number(this.formularioResolucion.value.numeroFinal);
    notaDebitoCredito.prefijo = this.formularioResolucion.value.prefijo;
    notaDebitoCredito.trTipoId = Number(this.formularioResolucion.value.trTipoId);
    return notaDebitoCredito;
  }

  /**
   * Metodo qeu consume y valida la respuesta del servicio de creacion de resolucion
   * @Param notaEnviar, NotasDebitoCredito => objeto con la informacion a crear
   */

  private crearResolucion(notaEnviar: NotasDebitoCredito) {
    this.service.crearResolucionNotas(notaEnviar).subscribe(
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

  private editarResolucion(notaEnviar: NotasDebitoCredito) {
    this.service.editarResolucionNotas(notaEnviar).subscribe(
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
   * Metodo que valida si el prefijo de la resolucion debe seguir un consecutivo
   */

  public validarConsecutivoPrefijo() {
    if (this.accion != "editar") {
      if (this.formularioResolucion.value.prefijo) {
        let buscarPrefijo: NotasDebitoCredito[] = this.notas.filter(data => data.prefijo == this.formularioResolucion.value.prefijo);
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

}
