import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { NotasDebitoCredito } from '../../../models/resoluciones/notasDebitoCredito';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ModalCreaEditaComponent } from '../modal-crea-edita/modal-crea-edita.component';
import { ResolucionesNotasService } from 'src/app/services/resolucionesNotas.service';
import { Resultado } from '../../../models/resultado';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tabla-resoluciones',
  templateUrl: './tabla-resoluciones.component.html',
  styleUrls: ['./tabla-resoluciones.component.css']
})
export class TablaResolucionesComponent implements OnInit {

  public bsModalRef: BsModalRef;
  public page: number;
  @Input() notas: NotasDebitoCredito[];
  @Input() compania: number;
  @Input() sucursal: number;
  @Input() producto: String;
  @Input() tipoNota: String;
  @Input() esCrear: boolean;
  @Input() esEditar: String;
  @Output() reiniciarServicio = new EventEmitter<boolean>();

  constructor(
    public modalService: BsModalService,
    private service: ResolucionesNotasService
  ) {

   }

  ngOnInit() {
  }

  /*
   Metodo que abre modal para agregar resolucion
  */

  agregarResolucion() {
    let titulo: String = this.validarHeaderModal(true);
    let nota: NotasDebitoCredito = this.validarCreacionResolucion();
    let initialState = {
      titulo: titulo,
      accion: "crear",
      nota: nota,
      notas: this.notas
    };
    this.bsModalRef = this.modalService.show(ModalCreaEditaComponent, { initialState, class: 'modal-lg', backdrop: 'static' });
    this.bsModalRef.content.closeBtnName = 'Close';
    this.bsModalRef.content.event.subscribe((res: boolean) => {
      if(res){
        this.reiniciarServicio.emit(res);
     }
    });
  }

  /**
   * Metodo que valida cual sera el siguiente consecutivo a crear para la resolucion
   */

  private validarCreacionResolucion(): NotasDebitoCredito {
    let nota = new NotasDebitoCredito();
    nota.compania = this.compania;
    nota.sucursal = this.sucursal;
    nota.producto = this.producto;
    nota.tipoNota = this.tipoNota;
    nota.numeroInicial = 1;
    let buscarTrue = this.notas.find(nota => nota.estado == 'TRUE');
    if (buscarTrue) {
      nota.estado = "FALSE";
      nota.auditoria = "FALSE"
      nota.contador = 0;
      nota.llave = 0;
    } else {
      nota.estado = "TRUE";
      nota.auditoria = "FALSE"
      nota.contador = 0;
      nota.llave = 0;
    }
    return nota;
  }

  /*
   Metodo que abre modal para editar resolucion
  */

  editarResolucion(nota: NotasDebitoCredito) {
    let titulo: String = this.validarHeaderModal(false);
    nota = this.validarConsecutivoEdicion(nota);
    let initialState = {
      titulo: titulo,
      accion: "editar",
      nota: nota,
      notas: this.notas
    };
    this.bsModalRef = this.modalService.show(ModalCreaEditaComponent, { initialState, class: 'modal-lg', backdrop: 'static' });
    this.bsModalRef.content.closeBtnName = 'Close';
    this.bsModalRef.content.event.subscribe((res: boolean) => {
        if(res){
           this.reiniciarServicio.emit(res);
        }
    });
  }

  /**
   * Metodo que valida el consecutivo al momento de editar
   */

  private validarConsecutivoEdicion(nota: NotasDebitoCredito): NotasDebitoCredito{
     let buscarPrefijo: NotasDebitoCredito[] = this.notas.filter(data => data.prefijo == nota.prefijo);
     let listaNumerosFinales: number[] =[];
     for(let sumar of buscarPrefijo){
       if(sumar.llave != nota.llave){
        listaNumerosFinales.push(sumar.numeroFinal);
       }
     }
     if(listaNumerosFinales.length > 0){
      let numeroMayor: number = Math.max(...listaNumerosFinales);
      nota.numeroInicial = numeroMayor + 1;
     }
     return nota;
  }

  /**
   * Metodo que valida el mensaje de la modal a abrir
   * @Param accion, boolean => true para crear y false para editar
   * @Return String => nombre de el encabezado de la modal
   */

  private validarHeaderModal(accion: boolean): String {
    let titulo: String;
    if (accion) {
      if (this.tipoNota == "credito") {
        titulo = "crear resolución credito";
      } else {
        titulo = "crear resolución debito"
      }
    } else {
      if (this.tipoNota == "credito") {
        titulo = "editar resolución credito";
      } else {
        titulo = "editar resolución debito"
      }
    } return titulo;
  }

  /**
   * Metodo que valida la deshabilitacion de lboton de edicion de acuerdo al estado y numeracion
   * @Param nota, NotasDebitoCredito => objeto con los datos a vlidar
   * @Return boolena => boolenao que define si se desactiva o no le boton de editar
   */

  public validarInactivacionBotonEditar(nota: NotasDebitoCredito): boolean {
    return nota.estado == 'FALSE' && nota.auditoria == "FALSE" && (nota.numeroFinal == nota.contador);
  }

  /**
   * Metodo que valida la deshabilitacion de lboton de eliminar de acuerdo al estado y numeracion
   * @Param nota, NotasDebitoCredito => objeto con los datos a validar
   * @Return boolena => boolenao que define si se desactiva o no le boton de editar
   */

   public validarInactivacionBotonEliminar(nota: NotasDebitoCredito): boolean {
    return nota.estado == 'TRUE' || (nota.auditoria == "FALSE" && nota.estado == 'FALSE' && (nota.numeroFinal == nota.contador));
  }

  /**
   * Metood que elimina la reoslucion de notas
   * @Param nota, NotasDebitoCredito => objeto con la informacion de notas
   */

  public eliminarResolucion (nota: NotasDebitoCredito){
    this.service.eliminarResolucionNotas(nota).subscribe(
      (resp: Resultado) => {
        if (resp.succes) {
          Swal.fire("Trabajo satisfactorio", resp.message, "success");
          this.reiniciarServicio.emit(true);
        } else {
          Swal.fire("Ha ocurrido un error", resp.message, "warning");
        }
      }
    );
  }

}
