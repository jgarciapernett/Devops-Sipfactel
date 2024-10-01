import {
  Component,
  OnInit,
  ViewChild,
  Input,
  SimpleChanges,
  Output,
  EventEmitter,
} from "@angular/core";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";

// Servicios
import { ConfiguracionService } from "../../../../services/configuracion.service";
import { UtilidadesService } from "../../../../services/utilidades.service";

// Modelos
import { opcion } from "src/app/models/opcion";

// Rutas
import { Router, ActivatedRoute } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import Swal from 'sweetalert2';

@Component({
  selector: "conf-modal-editar",
  templateUrl: "./modal-editar.component.html",
})
export class ModalEditarComponent implements OnInit {
  @Input() configuracionEntrada: opcion;
  @Output() modalHide = new EventEmitter<string>();
  @Output() refreshLista = new EventEmitter<any>();
  public mensaje: string = 'texto';
  public refresh: any; 
  public configuracion: opcion;
  public frmEditarConfiguracion: FormGroup;
  public ngEditarConfiguracion: NgForm;
  public mostrarEditar: boolean;

  constructor(
    private router: Router,
    private configuracionService: ConfiguracionService,
    public utilidadesService: UtilidadesService,
    public formBuilder: FormBuilder,
    private rutaActiva: ActivatedRoute,
    private toastr: ToastrService
  ) {
    this.configuracion = new opcion()
  }

  ngOnInit() {
    this.validaciones();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.configuracionEntrada.currentValue != undefined) {
      this.configuracion = changes.configuracionEntrada.currentValue;
    }
  }

  cancelar() {
    // this.configuracion = new opcion();
  }

  // Se modifica el servicio agregando dos output para actualizar y cerrar el modal.

  editarConfiguracion() {
    this.configuracionService
      .editarLaConfiguracion(this.configuracion)
      .subscribe(
        (res: any) => {
          Swal.fire({
            type: "success",
            title: "Operación Exitosa",
            text: res.mensajeEstado
          });
          if (res = true){
            this.modalHide.emit(this.mensaje);
            this.refreshLista.emit(this.refresh)
           }
        },
        (error: HttpErrorResponse) => {
          console.error(error.message);
        }
      );
  }

  validaciones() {
    this.frmEditarConfiguracion = this.formBuilder.group({
      txtCodigo: this.formBuilder.control("", [Validators.required]),
      txtNombre: this.formBuilder.control("", [Validators.required]),
      txtEstado: this.formBuilder.control("", [Validators.required]),
      
    });
  }
}
