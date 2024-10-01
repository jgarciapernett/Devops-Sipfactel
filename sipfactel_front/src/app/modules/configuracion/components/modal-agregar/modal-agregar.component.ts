import { Component,
         ViewChild,
         OnInit,
         Input,
         Output,
         EventEmitter,
         SimpleChanges, 
         OnChanges,
         Host} from "@angular/core";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";

// Servicios
import { ConfiguracionService } from "../../../../services/configuracion.service";
import { UtilidadesService } from "../../../../services/utilidades.service";
import { ToastrService } from "ngx-toastr";

// Models
import { opcion } from "src/app/models/opcion";

// Rutas
import { Router } from "@angular/router";
import { ActivatedRoute, Params } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { InicioComponent } from '../inicio/inicio.component';
import Swal from 'sweetalert2';

@Component({
  selector: "conf-modal-agregar",
  templateUrl: "./modal-agregar.component.html"
})
export class ModalAgregarComponent implements OnInit, OnChanges{
  public mensaje: string = 'sddsdsd';
  public refresh: any; 
  public configuracion: opcion;
  public resultado: string;
  public frmAgregarConfiguracion: FormGroup;
  @Input() idPadre: number;
  @Input() campoTexto: string;
  @Output() modalHide = new EventEmitter<string>();
  @Output() refreshLista = new EventEmitter<any>();
  @ViewChild("ngAgregarConfiguracion", { static: false })
  public ngAgregarConfiguracion: NgForm;


  constructor(
    private rutaActiva: ActivatedRoute,
    private router: Router,
    private ConfiService: ConfiguracionService,
    public utilidadesService: UtilidadesService,
    public formBuilder: FormBuilder,
    private toastr: ToastrService
  ) {
    this.configuracion = new opcion()
    this.configuracion.codigo = ""
  }

  ngOnInit() {
    this.validaciones();
  }

    
  ngOnChanges(changes: SimpleChanges): void {

  this.configuracion.padre=  changes.idPadre.currentValue;
  this.configuracion.sistema = changes.campoTexto.currentValue
  }

  guardarCambios() {
    if (
      !this.utilidadesService.esFormularioValido(this.ngAgregarConfiguracion)
    ) {
      return;
    }
    this.agregarConfiguracion();
  }

  cancelar() {
    this.configuracion.sistema = this.configuracion.sistema
    this.configuracion.nombre = '';
    this.configuracion.codigo = '';
    this.configuracion.estado = undefined;
  }

  agregarConfiguracion() {
      this.ConfiService.insertarConfiguracion(this.configuracion).subscribe(
        (res: any) => {
          Swal.fire({
            type: "success",
            title: "Operación Exitosa",
            text: res.mensajeEstado
          });
          if (res = true){
            this.configuracion.sistema = this.configuracion.sistema
            this.configuracion.nombre = '';
            this.configuracion.codigo = '';
            this.configuracion.estado = undefined;
            this.refreshLista.emit(this.refresh)
            this.modalHide.emit(this.mensaje);
        }

        },
        (error: HttpErrorResponse) => {

          console.error(error.message);
        }
      );
    
  }

  validaciones() {
    this.frmAgregarConfiguracion = this.formBuilder.group({
      cod: this.formBuilder.control("", [Validators.required]),
      txtNombre: this.formBuilder.control("", [Validators.required]),
      txtSistema: this.formBuilder.control("", [Validators.required]),
      txtEstado: this.formBuilder.control(undefined, [Validators.required]),
    });
  }

}
