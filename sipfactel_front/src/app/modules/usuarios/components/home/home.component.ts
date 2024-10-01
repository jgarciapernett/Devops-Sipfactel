import { Component, OnInit, SimpleChanges, Input } from "@angular/core";
import { FormControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Router, RouterModule } from "@angular/router";

//#region servicios
import { UsuariosService } from "src/app/services/usuarios.service";
import { PaginacionService } from "src/app/services/paginacion.service";
import { UtilidadesService } from "src/app/services/utilidades.service";
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
//#endregion

//#region modelo
import { Usuarios } from "src/app/models/usuarios";
//#endregion

//#region animaciones
import { fundido } from "src/app/animation/animation";
//#endregion animaciones

//#region bibliotecas
import { Subject } from "rxjs/internal/Subject";
import { finalize } from "rxjs/operators";
import { saveAs } from "file-saver";
import Swal from "sweetalert2";
//#endregion

@Component({
  selector: "usuarios-home",
  templateUrl: "./home.component.html",
  animations: [fundido],
  providers: [UsuariosService, UtilidadesService, PaginacionService],
})
export class HomeComponent implements OnInit {
  public usuariosFiltro$: Subject<Usuarios[]> = new Subject<Usuarios[]>();
  public filtroTabla = new FormControl("");
  public usuarios: Usuarios[] = [];
  public datosDeUsuario: Usuarios;
  public txtBuscar: string;
  public paso: number;
  public idUsuarioAlEditar: number;
  public cambiarPasoAlIrAtras: any;
  public esAgregar: boolean;
  public mostrarFormularios: boolean;
  public botonDesactivado: boolean;
  public frmBusqueda: FormGroup;

  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;

  constructor(
    private paginacionService: PaginacionService,
    private utilidadesService: UtilidadesService,
    private usuariosService: UsuariosService,
    private SpinnerService: Ng4LoadingSpinnerService,
    private toastr: ToastrService,
    private router: Router,
    public formBuilder: FormBuilder,

  ) {
    this.inicializaciones();
    this.ObtenerPermisos('usuarios');

  }

  ngOnInit() {
    this.validaciones(),
    this.obtenerTodosLosUsuarios()
  }

  obtenerTablaUsuarios() {

    if (this.txtBuscar.length === 0){
      this.obtenerTodosLosUsuarios();
    }

   else if (this.txtBuscar.length >= 1) {
      this.SpinnerService.show();
      this.usuariosService
        .obtenerUsuariosRegistrados(this.txtBuscar)
        .pipe(
          finalize(() => this.SpinnerService.hide()) // Solicitud completada con éxito o fallo
        )
        .subscribe((res: any) => {
          this.usuarios = res.resultado;
          this.botonDesactivado = true;
          this.aplicarFiltroTabla();
          this.mostrarFilasPagina();
        });
    }this.txtBuscar = ''
  }

  obtenerTodosLosUsuarios() {

    this.SpinnerService.show();
    this.usuariosService.buscarTodosLosUsuarios()
      .pipe(
        finalize(() => this.SpinnerService.hide()) // Solicitud completada con éxito o fallo
      )
      .subscribe((res: any) => {
        this.usuarios = res.resultado;
        this.botonDesactivado = true;
        this.aplicarFiltroTabla();
        this.mostrarFilasPagina();
      });
  }

  descargarExcelUsuarios() {
    this.usuariosService.DescargarExcel().subscribe((res: any) => {
      saveAs(res, "Usuarios" + "." + "xlsx");
      Swal.fire({
        type: "success",
        title: "Operación Exitosa",
        text: "Archivo descargado correctamente",
      });
    });
  }

  aplicarFiltroTabla() {
    this.utilidadesService.filtrarTabla(
      this.filtroTabla,
      this.usuarios,
      this.usuariosFiltro$
    );
  }

  cargarPagina(pagina: number) {
    this.paginacionService.cargarPagina(
      pagina,
      this.usuariosFiltro$,
      this.usuarios
    );
  }

  mostrarFilasPagina() {
    this.paginacionService.mostrarFilasPagina(
      this.usuariosFiltro$,
      this.usuarios
    );
  }

  obtenerFormularioAgregar($event) {
    this.router.navigate(["./Usuarios/agregar"]);
    this.esAgregar = $event
    if (this.esAgregar !== true) {
      this.obtenerTablaUsuarios();
    }
  }

  obtenerFormularioEditar(id: number) {
    this.router.navigate(["Usuarios/editar", id]);
  }

  inicializaciones() {
    this.txtBuscar = "";
    this.datosDeUsuario = new Usuarios();
    this.datosDeUsuario.esAgregar = true;
    this.mostrarFormularios = false;
    this.botonDesactivado = false;
  }


  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que tenga
    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    menu.forEach(item => { // Mapeo de los modulos y sus respectivos permisos
      item.menuHijos.map(alias => {
        if( alias.menuAlias === menuAlias){
          this.esCrear = alias.crear;
          this.esConsultar = alias.consultar;
          this.esEditar = alias.modificar;
        }
       else if (item.menuAlias === menuAlias ) {

          this.esCrear = item.crear;
          this.esConsultar = item.consultar;
          this.esEditar = item.modificar;
        }
      })


    });



  }
  validaciones() { // Valida la longitud del campo nombre del rol
    this.frmBusqueda = this.formBuilder.group({
      usuaUsuario: this.formBuilder.control('', [Validators.maxLength(50),Validators.minLength(3)]),


    });


  }
}
