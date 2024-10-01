import { Component, OnInit, SimpleChanges } from "@angular/core";
import {
  ReactiveFormsModule,
  FormGroup,
  FormBuilder,
  Validators,
  NgForm,
} from "@angular/forms";

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
import { Router, ActivatedRoute, Params } from "@angular/router";
//#endregion

@Component({
  selector: "usuarios-editar",
  templateUrl: "./editar-usuario.component.html",
})
export class EditarUsuarioComponent implements OnInit {
  public frmEditarUsuario: FormGroup;
  public ngEditarUsuario: NgForm;
  public datosUsuario: Usuarios;
  public listaRoles: Lista[];
  public rolElegido: number;
  public estadoElegido: number;
  public idRecibido: number;

  constructor(
    private usuariosServices: UsuariosService,
    public formBuilder: FormBuilder,
    public utilidadesService: UtilidadesService,
    public router: Router,
    public activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.inicializaciones();
    this.obtenerListaRoles();
    this.validacionesUsuarios();
    this.parametrosRecibidos();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.idRecibido.currentValue !== undefined) {
      this.datosUsuario = changes.idRecibido.currentValue;
    }
  }

  parametrosRecibidos() {
    this.idRecibido = Number(this.activatedRoute.snapshot.params.id.toString());
    this.usuariosServices
      .obtenerRegistroPorId(this.idRecibido)
      .subscribe((res: any) => {
        var usuarioEditar: Usuarios = Object.assign(new Usuarios(), res.resultado);
        this.datosUsuario = usuarioEditar;
      });
    if (this.idRecibido === NaN) {
      Swal.fire({
        type: "warning",
        text: "Valor enviado incorrecto",
      });
    }
  }

  editarUsuario(Object: Usuarios) {
    this.usuariosServices
      .EditarUsuario(this.datosUsuario)
      .subscribe((res: any) => {
        Swal.fire({
          type: "success",
          title: "Operación Exitosa",
          text: "Registro guardado correctamente"
        });
        this.inicializaciones();
        this.router.navigate(['Usuarios/home']);
      }),
      (error: HttpErrorResponse) => {
        console.error(error.message);
      };
  }

  obtenerListaRoles() {
      this.usuariosServices.obtenerRol().subscribe((res: any) => {
        this.listaRoles = res.resultado;
      });
  }

  cancelar() {
    this.router.navigate(["Usuarios/home"]);
  }

  validacionesUsuarios() {
    this.frmEditarUsuario = this.formBuilder.group({
      txtNombreUsuario: this.formBuilder.control("", [Validators.required]),
      txtUsuarioNombres: this.formBuilder.control("", [Validators.required]),
      txtUsuarioApellidos: this.formBuilder.control("", [Validators.required]),
      txtUsuarioCorreo: this.formBuilder.control("", [
        Validators.required,
        Validators.email,
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

  inicializaciones() {
    this.datosUsuario = new Usuarios();
    this.rolElegido = 0;
    this.estadoElegido = 0;
  }
}
