import { Component, OnInit, DoCheck, Host } from "@angular/core";
import { Router } from "@angular/router";
// modelo
import { Usuario } from "src/app/models/usuario";
// servicios
import { LoginJwtService } from "../../services/login-jwt.service";
import { AppComponent } from "src/app/app.component";
import { JwtService } from "src/app/services/jwt.service";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"],
  providers: [AppComponent],
})
export class NavbarComponent implements OnInit, DoCheck {
  // tslint:disable-next-line: variable-name
  public _usuario: Usuario;
  public cantidadNotificaciones: number;
  public isNotificaciones: boolean;

  constructor(
    @Host() private appComponet: AppComponent,
    private router: Router,
    private servicioLogin: LoginJwtService,
    private loginService: LoginJwtService,
    public jwt_service: JwtService
  ) {}

  ngOnInit() {
    this.isIdentity();
  }

  ngDoCheck() {
    this.isIdentity();
  }

  cerrarSesion() {
    this.loginService.logOut();
    // this.jwt_service.expirationTime()
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

  isIdentity() {
    this._usuario = new Usuario();
    if (!this.servicioLogin.isIdentity()) {
      this._usuario = new Usuario();
      // this._usuario.nombre = '';
    } else {
      const user = JSON.parse(localStorage.getItem("sesion"));
      this._usuario.nombre = user.user.usuaUsuario;
    }
  }
  mostrarUOcultarSidebar() {
    const sidebar = document.querySelector("#sidebar") as HTMLElement;
    sidebar.classList.toggle("active");
  }
}
