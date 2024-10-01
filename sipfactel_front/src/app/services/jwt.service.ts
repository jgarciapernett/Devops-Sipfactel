import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { environment } from "../../environments/environment";

//#region models
import { Sesion } from "../models/sesion";
//#endregion models

//#region services
import { LoginService } from "./login.service";
import { SesionService } from "./sesion.service";
//#endregion services

//#region libraries
import * as _ from "lodash";
import { tap } from "rxjs/operators";
import * as jwt_decode from "jwt-decode";

//#endregion libraries

@Injectable({
  providedIn: "root",
})
export class JwtService {
  public urlBase = "";
  public timer: any;

  timeLeft: number = 900;
  interval;
  public httpOptions = new HttpHeaders().append(
    "Content-Type",
    "application/json; charset=UTF-8"
  );

  constructor(
    // tslint:disable: variable-name
    private http: HttpClient,
    private router: Router,
    public loginService: LoginService,
    private sesionService: SesionService
  ) {
    this.urlBase = environment.urlBaseServicio;
  }

  refreshToken(name?) {
    
    const url = `${this.urlBase}/api/auth/Newrefreshtoken`;

    const sesion = this.sesionService.obtenerSesion();
    const body = {
      refreshToken: sesion.refreshToken,
    };

    return this.http.post(url, body, { headers: this.httpOptions }).pipe(
      tap((res: Sesion) => {
        /*alertz
         * Limpiar valores vacíos o en blanco del objeto respuesta:
         */
        _.keys(res).forEach(
          (clave) => _.isNil(res[clave]) && delete res[clave]
        );

        res = _.extend(new Sesion(), res);
        _.extend(sesion, res);

        localStorage.setItem("sesion", JSON.stringify(sesion));
      })
    );
  }

  expirationTime(num, refreshtime) {
    this.interval = setInterval(() => {
      if (num > 0) {
        if (num == refreshtime) {
          this.pauseTimer();
          this.refreshToken().subscribe((res: any) => {
            localStorage.setItem("sesion", JSON.stringify(res.resultado));
          });
        }
      } else {
        num = num;
      }
    },  1000);
  }
  pauseTimer() {
    clearInterval(this.interval);
  }

  decodificarToken() {
    const sesion = JSON.parse(localStorage.getItem("sesion"));
    if (sesion === null) {
      this.pauseTimer();
      localStorage.clear();
      return false;
    } else if (sesion != null) {
      const token = sesion.accessToken;
      const decoded = jwt_decode(token);
      const num: number = decoded.exp - decoded.iat;
      const refreshtime = (num * 10) / 100;
      this.expirationTime(num, refreshtime);
    }
  }
}
