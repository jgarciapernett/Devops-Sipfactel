import {
  Component,
  OnInit,
  Output,
  EventEmitter,
} from "@angular/core";
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from "@angular/forms";

////#region servicios
import { UsuariosService } from "src/app/services/usuarios.service";
import { UtilidadesService } from "../../../../services/utilidades.service";
//#endregion

//#region modelos
import { Usuarios } from "src/app/models/usuarios";
import { Lista } from "src/app/models/lista";
//#endregion

//#region  directivas
import { selectInicial } from "src/app/directives/validators/select.validator";
import Swal from "sweetalert2";
import { HttpErrorResponse } from "@angular/common/http";
import { Router } from '@angular/router';
//#endregion

@Component({
  selector: "usuarios-agregar",
  templateUrl: "./agregar-usuario.component.html",
})
export class AgregarUsuarioComponent implements OnInit {
  @Output() esAgregar = new EventEmitter<boolean>();
  public frmAgregarUsuario: FormGroup;
  public datosUsuario: Usuarios;
  public listaRoles: Lista[];
  public rolElegido: number;
  public estadoElegido: number;

  constructor(
    private usuariosServices: UsuariosService,
    public formBuilder: FormBuilder,
    public utilidadesService: UtilidadesService,
    public router: Router
  ) {
    this.obtenerListaRoles();
  }

  ngOnInit() {
    this.inicializaciones();
    this.validacionesSucursal();
  }

  inicializaciones() {
    this.datosUsuario = new Usuarios();
    this.rolElegido = 0;
    this.estadoElegido = 0;
  }

  agregarUsuario() {
    this.usuariosServices.agregarUsuario(this.datosUsuario).subscribe(
      (res: any) => {
        Swal.fire({
          type: "success",
          title: "Operación Exitosa",
          text: "Registro guardado correctamente"
        });
        this.inicializaciones();
        this.router.navigate(['Usuarios/home']);
        this.esAgregar.emit(false);
      },
      (error: HttpErrorResponse) => {
        this.inicializaciones();
       
      }
    );
  }

  obtenerListaRoles() {
    if (this.rolElegido !== 0) {
      this.usuariosServices.obtenerRol().subscribe((res: any) => {
        this.listaRoles = res.resultado;
      });
    }
  }

  cancelar() {
    this.router.navigate(['Usuarios/home']);
  }

  validacionesSucursal() {
    this.frmAgregarUsuario = this.formBuilder.group({
      txtNombreUsuario: this.formBuilder.control("", [Validators.required]),
      txtUsuarioNombres: this.formBuilder.control("", [Validators.required]),
      txtUsuarioApellidos: this.formBuilder.control("", [Validators.required]),
      txtUsuarioCorreo: this.formBuilder.control("", [
        Validators.required,
        Validators.email,
        Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.+-]+\.[a-z]{2,3}$")
      ]),
      ddlRoles: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
      ddlEstado: this.formBuilder.control(0, [
        Validators.required,
        selectInicial,
      ]),
    });
  }
}
