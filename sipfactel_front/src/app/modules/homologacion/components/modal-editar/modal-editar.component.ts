import { Component, OnInit, Input ,SimpleChanges, Output, EventEmitter } from "@angular/core";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";

// Servicios
import { homologacionService } from "../../../../services/homologacion.service";
import { UtilidadesService } from "../../../../services/utilidades.service";

// Modulos

// Modelos

import { homologacion } from '../../../../models/homologacion';

// Bibliotecas
import { HttpErrorResponse } from "@angular/common/http";
import Swal from 'sweetalert2';
import { selectInicial } from '../../../../directives/validators/select.validator';
import { Lista } from '../../../../models/lista';



@Component({
  selector: "homol-modal-editar",
  templateUrl: "./modal-editar.component.html"
})
export class ModalEditarComponent implements OnInit {
  @Input () public homol: homologacion;
  @Output() VolverTablaHomologaciones: EventEmitter<any> = new EventEmitter<any>();
  @Output() modalHide = new EventEmitter<string>();
  
  public frmEditarHomologacion: FormGroup;
  public ngEditarHomologacion: NgForm;
  public homologacion: homologacion;
  public formulario : boolean
  public refresh: any;
  public mensaje: string ;
  public fuentes:Lista[]

  constructor(
    private homologacionService: homologacionService,
    public utilidadesService: UtilidadesService,
    public formBuilder: FormBuilder,
 
  ) {
    this.homologacion = new homologacion();
    this.obtenerFuente()
  
  }

  ngOnInit() {
    this.validaciones();
  }

  ngOnChanges(changes: SimpleChanges): void { // Captura el cambi del id
  
    if(changes.homol.currentValue != undefined)
   this.homologacion = changes.homol.currentValue;  
  }
  
 
  
  cancelar() { // Cancela la operacion de editar y cierra el formulario
    this.modalHide.emit(this.mensaje);
  }

  
  
  editarHomologacion() { // Edita el registro seleccionado 
  
    this.homologacionService.editarHomologacion(this.homologacion).subscribe(
      
        (res: any) => { // Solicitud con exitó o fallo
          this.modalHide.emit(this.mensaje);
          this.VolverTablaHomologaciones.emit(this.refresh);
          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            text: res.mensajeEstado,
          });
        },
        (error: HttpErrorResponse) => {


          
        }
      );
    }
    obtenerFuente(){
      
      this.homologacionService.obtenerListaFuentes().subscribe((res:any ) => {
        this.fuentes = res.resultado
      })
    }
    validaciones() { // Validacion de los campos requeridos del formulario
      this.frmEditarHomologacion = this.formBuilder.group({
        txtFuente: this.formBuilder.control( 0, [Validators.required,selectInicial]),
        txtOriginal: this.formBuilder.control("", [Validators.required]),
        txtHomologado: this.formBuilder.control("", [Validators.required]),
        txtDescripcion: this.formBuilder.control("", [Validators.required]),
        txtEstado: this.formBuilder.control("", [Validators.required,selectInicial]),
      });
    }
    
  }
  
