import {
  Component,
  ViewChild,
  OnInit,
  Input,
  SimpleChanges,
  Output,
  EventEmitter
} from "@angular/core";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";

// Servicios
import { homologacionService } from "../../../../services/homologacion.service";
import { UtilidadesService } from "../../../../services/utilidades.service";
import { ToastrService } from "ngx-toastr";

// Models
import { homologacion } from "src/app/models/homologacion";

// Rutas
import { Router } from "@angular/router";
import { ActivatedRoute, Params } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import Swal from 'sweetalert2';
import { Lista } from '../../../../models/lista';
import { selectInicial } from '../../../../directives/validators/select.validator';

@Component({
  selector: "homol-modal-agregar",
  templateUrl: "./modal-agregar.component.html"
})
export class ModalAgregarComponent implements OnInit {
  @Input() conf: number;
  @Output() VolverTablaHomologaciones: EventEmitter<any> = new EventEmitter<any>();
  @Output() modalHide = new EventEmitter<string>();

  public mensaje: string ;
  public resultado: string;
  public refresh: any;
  public frmAgregarHomologacion: FormGroup;
  public homologacionObj: homologacion;
  public regreso: any;
  public pattern: number;
  public fuentes : Lista []

  constructor(
    private rutaActiva: ActivatedRoute,
    private router: Router,
    private homoService: homologacionService,
    public utilidadesService: UtilidadesService,
    public formBuilder: FormBuilder,
    private toastr: ToastrService
  ) {

    this.obtenerFuente()
    this.homologacionObj = new homologacion()
  }

  ngOnInit() {

    this.validaciones();
    this.homologacionObj.fuente == 0
  }
  obtenerFuente(){
    
    this.homoService.obtenerListaFuentes().subscribe((res:any ) => {
      this.fuentes = res.resultado
    })
  }

  ngOnChanges(changes: SimpleChanges): void { // Captura los cambios del id
   
      this.homologacionObj.conf = changes.conf.currentValue;
    
  }



  cancelar() { // cancela la operacion de agregar y cierra el formulario
    this.modalHide.emit(this.mensaje);
    this.validaciones();
  }

  agregarHomologacion() { // Servicio que agrega la nueva homologacion
    this.homoService.insertarHomologacion(this.homologacionObj).subscribe(
      (res: any) => { // Solicitud con exitó o fallo
        this.VolverTablaHomologaciones.emit(this.refresh);
        this.modalHide.emit(this.mensaje);
        Swal.fire({
          type: 'success',
          title: 'Operación Exitosa',
          text: res.mensajeEstado
        });
      },
      (error: HttpErrorResponse) => {},
      );    
      this.validaciones()

  }

  validaciones() {// Validacion de los campos requeridos del formulario
    this.frmAgregarHomologacion = this.formBuilder.group({
      txtFuente: this.formBuilder.control( 0, [Validators.required,selectInicial]),
      txtOriginal: this.formBuilder.control("", [Validators.required]),
      txtHomologado: this.formBuilder.control("", [Validators.required]),
      txtDescripcion: this.formBuilder.control("", [Validators.required]),
      txtEstado: this.formBuilder.control(undefined, [Validators.required,selectInicial]),

    });
  }
}
