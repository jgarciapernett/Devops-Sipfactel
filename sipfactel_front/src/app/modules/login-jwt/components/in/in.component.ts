import { Component, OnInit, isDevMode } from "@angular/core";
import { Router } from "@angular/router";

//#region modelos
import { Login } from "../../../../models/login";
import { Usuario } from "src/app/models/usuario";
//#endregion modelos

//#region servicios
import { LoginJwtService } from "../../../../services/login-jwt.service";
import { UtilidadesService } from "../../../../services/utilidades.service";
//#endregion servicios

//#region servicios externos
import { ToastrService } from "ngx-toastr";
//#endregion servicios externos

//#region bibliotecas
import Swal from "sweetalert2";
import * as _ from "lodash";
import { Sesion } from "src/app/models/sesion";
import { Menu } from "src/app/models/menu";

import { HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { HttpErrorResponseInit } from "src/app/interfaces/http-error-response-init.interface";
import { ErrorNoControlado } from "../../../../models/error-no-controlado";
import { Respuesta } from "../../../../models/respuesta";
import { JsonPipe } from "@angular/common";
import { Session } from "protractor";
import { tokenValidateUtils } from "src/app/utils/tokenValidateUtils";
import { JwtService } from "src/app/services/jwt.service";
import {
  FormBuilder,
  FormGroup,
  NgForm,
  Validators,
  AbstractControl,
} from "@angular/forms";
//#endregion bibliotecas

@Component({
  selector: "app-in",
  templateUrl: "./in.component.html",
  styleUrls: ["./in.component.css"],
})
export class InComponent implements OnInit {
  // tslint:disable: variable-name
  public objlogin: Login;
  public _usuario: Usuario;
  private __sesion: Sesion;
  public vistas: string[] = [];
  public infoUserTemporal = "../../assets/sesionFake.json";
  public frmSesion: FormGroup;

  constructor(
    private _loginService: LoginJwtService,
    private _jwtService: JwtService,
    private toastr: ToastrService,
    private _router: Router,
    public utilsService: UtilidadesService,
    public tokenValidate: tokenValidateUtils,
    public formBuilder: FormBuilder
  ) {
    this.inicializarObjLogin();
    this._jwtService.decodificarToken(); // this.validaciones();
  }

  ngOnInit() {
    this.validaciones();
    this._loginService.logOut();
    localStorage.clear();
    localStorage.setItem("IsIdentity", "false");
  }

  inicializarObjLogin() {
    this.objlogin = new Login(); //nuevo ajuste
  }
  
  iniciarSesion() {
    
    if (this.objlogin.contrasena === "" || this.objlogin.usuario === "") {
      Swal.fire({
        type: "warning",
        title: "Datos Incompletos",
        text: "¡Por ingrese los campos obligatorios!",
        footer: "",
      });
      return;
    }

    this._loginService
      .logIn(this.objlogin, false)
      .pipe(
        catchError((errorResponse: HttpErrorResponse) => {
          const errorResponseParams = { errorResponse };
          this.configurarErrorResponse(errorResponseParams);
          throw errorResponseParams.errorResponse;
        })
      )
      .subscribe(
        (res1: Respuesta) => {
          localStorage.setItem('alerts',JSON.stringify(res1.resultado))
          let res = _.extend(new Sesion(), res1.resultado);

          window.localStorage.getItem(res.accessToken);
          /**
           * @author Camilo Soler
           * @Description (Isdevmode) permite validar si la aplicacion esta en modo desarrollo o en despliegue
           * atraves de esto se pueden inyectar  metodos,respuestas etc.
           */

          if (_.isNil(res)) {
            if (res == undefined) {
              // this.tokenValidate.saveLocalStorage("sesion",res.menus.map(menu =>{return menu}))
            }
            localStorage.setItem("IsIdentity", "false");
            this._loginService.establecerLogueado(false);

            return false;
          } else {
            this.__sesion = _.extend(new Sesion(), res);
            this._usuario = this.__sesion.user;

            localStorage.setItem("IsIdentity", "true");
            localStorage.setItem("sesion", JSON.stringify(this.__sesion));
            this.__sesion.listMenu.forEach((itemPadre) => {
              if (itemPadre.menuHijos.length > 0) {
                itemPadre.menuHijos.forEach((itemMenuHijo) => {
                  this.vistas.push(itemMenuHijo.menuAlias);
                });
              } else {
                this.vistas.push(itemPadre.menuAlias);
              }
            });

            localStorage.setItem("vistas", JSON.stringify(this.vistas));
            this.toastr.success(
              "Inició sesión correctamente",
              "Operación exitosa "
            );
            this._router.navigate(["/home"]);
            this._loginService.establecerLogueado(true);
            this._jwtService.decodificarToken();
            return true;
          }
        },
        (error: HttpErrorResponse) => {}
      );
  }


  configurarErrorResponse(params: { errorResponse: HttpErrorResponse }) {
    const error = _.extend(
      new ErrorNoControlado(),
      params.errorResponse.error as ErrorNoControlado
    );

    if (
      error.status === 401 &&
      error.error === "Unauthorized" &&
      error.message === "Bad credentials"
    ) {
      const respuesta = new Respuesta();
      respuesta.codigoEstado = error.status.toString();
      respuesta.mensajeEstado = "Usuario y/o contraseña no válidos";
      respuesta.resultado = respuesta.mensajeEstado;
      const errorResponseProps: HttpErrorResponseInit = {
        error: respuesta,
      };
      params.errorResponse = new HttpErrorResponse(
        _.extend({}, params.errorResponse, errorResponseProps)
      );
    }
    this.inicializarObjLogin();
  }

  animar() {
    const d = document.querySelector(".rutbutton");
    d.className = "rutbutton-left";
  }

  validaciones() {
    this.frmSesion = this.formBuilder.group({
      txtUsuario: this.formBuilder.control("", [
        Validators.required,
        Validators.minLength(12),
      ]),
      txtContrasenia: this.formBuilder.control("", [
        Validators.required,
        Validators.minLength(12),
      ]),
    });
  }
}
