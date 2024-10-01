import {
  Component,
  OnInit,
  OnChanges,
  DoCheck,
  ViewChild,
} from "@angular/core";

//librerias externas
import { Idle } from "@ng-idle/core";
import { Keepalive } from "@ng-idle/keepalive";

import { LoginJwtService } from "./services/login-jwt.service";

// declare var $: any;
import * as $ from "jquery";
import { Router } from "@angular/router";

import { JwtService } from "./services/jwt.service";
import { IdleConfigurationService } from "./services/idleConfiguration.service";
import { ModalDirective } from "ngx-bootstrap/modal";
import { log } from "util";
import { Sesion } from "./models/sesion";

// tslint:disable-next-line: no-conflicting-lifecycle
@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit, DoCheck {
  public mostrarMenus: boolean;
  public isDentity: boolean;
  public refresh: any;
  idleState = "Sin sesion";
  timedOut = false;
  lastPing?: Date = null;
  title = "angular-idle-timeout";
  public modalOptions = {
    backdrop: "static",
    ignoreBackdropClick: true,
  };

  @ViewChild("childModal", { static: false }) childModal: ModalDirective;

  constructor(
    // tslint:disable-next-line: variable-name
    private loginService: LoginJwtService,
    private jwtService: JwtService,
    private idle: Idle,
    private keepalive: Keepalive,
    private router: Router,
    public timer: IdleConfigurationService
  ) {
    this.ConfigurarInactividad();

    // this.jwtService.decodificarToken();
  }

  ngOnInit() {
    this.inicializarJQuery();
    this.mostrarUocultarMenus();
    this.verificarSesion();
    this.idle.watch();
  }

  verificarSesion() {
    const sesion = JSON.parse(localStorage.getItem("sesion"));
    if (sesion !== null || sesion !== undefined) {
      this.jwtService.decodificarToken();
    }
  }
  reset() {
    this.idle.watch();
    this.timedOut = false;
  }

  hideChildModal(): void {
    this.childModal.hide();
  }

  stay() {
    this.reset();
    this.childModal.hide();
    this.jwtService.refreshToken().subscribe((res:any) => {

      localStorage.setItem("sesion", JSON.stringify(res.resultado));
    });
  }

  logout() {

    this.childModal.hide();
    this.loginService.establecerLogueado(false);
    this.childModal.hide();
    this.jwtService.pauseTimer()
    this.router.navigate(["/"]);
    localStorage.clear();
  }

  //-------------------------------------------------------------------------------------------------

  noHayAtras() {
    window.history.forward();
  }

  inicializarJQuery() {
    jQuery(() => {
      $('[data-toggle="tooltip"]').tooltip();

      window.history.forward();
      window.onload = this.noHayAtras;
      window.onpageshow = (event: PageTransitionEvent) => {
        if (event.persisted) {
          this.noHayAtras();
        }
      };
      window.onunload = undefined;
    });
  }

  ngDoCheck() {
    this.mostrarUocultarMenus();
  }

  mostrarUocultarMenus() {
    if (this.loginService.esLogeado()) {
      this.mostrarMenus = true;
    } else {
      this.mostrarMenus = false;
    }
  }

  ConfigurarInactividad() {
    this.timer.timerIdle(180);
    this.idle.onIdleEnd.subscribe(() => {
      this.reset();
      this.jwtService.refreshToken();
    });
    this.idle.onTimeout.subscribe(() => {
      this.idleState = "Timed out!";
      this.timedOut = true;
      if (this.timedOut == true) {
        this.childModal.hide();
      }

      this.loginService.logOut();
    });

    this.loginService.obtenerLogueado().subscribe((esUsuarioLogueado) => {
      if (esUsuarioLogueado == true) {
        this.idle.onIdleStart.subscribe(() => {
          this.idleState = "You've gone idle!";
          this.childModal.show();
        });
        this.idle.onTimeoutWarning.subscribe((countdown) => {
          this.idleState = "Su sesión expirará en " + countdown + " segundos";
        });
      } else {
        this.idle.stop();

      }
    });
    this.reset();
  }
}
