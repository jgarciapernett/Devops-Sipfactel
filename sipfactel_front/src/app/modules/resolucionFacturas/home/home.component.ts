import { Component, OnInit } from '@angular/core'
import { ResolucionFacturas } from '../../../models/resoluciones/ResolucionFacturas';
import { ModalCreaEditaResolucionFacturaComponent } from '../modal-crea-edita-resolucion-factura/modal-crea-edita-resolucion-factura.component'
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { selectInicial } from './../../../directives/validators/select.validator';
import { ReportesService } from 'src/app/services/reportes.service';
import { SucursalService } from 'src/app/services/sucursal.service';
import { ResolucionesService } from 'src/app/services/resoluciones.service';
import { sucursal } from '../../../models/desplegableSucu';
import { Lista } from 'src/app/models/lista';
import { UtilidadesService } from 'src/app/services/utilidades.service';
import { Resultado } from '../../../models/resultado';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import Swal from 'sweetalert2';


@Component({
  selector: "app-home",
  templateUrl: "./home.component.html"
})
export class HomeComponent implements OnInit {

  public mostrarTablaResoluciones: boolean = false;
  public listaSucursales: sucursal[];
  public listaCompanias: Lista[];
  public productos: Lista[];
  public formConsulta: FormGroup;
  public listaResolucionFacturas: ResolucionFacturas[];
  public page: number;
  public botonConsultaPresionado: boolean = false;
  private respuestaEliminacion: Resultado;
  public bsModalRef: BsModalRef;

  private sucursal: number;
  private compania: number;
  private producto: string;

  public esCrear: boolean;
  public esConsultar: boolean;
  public esEditar: boolean;

  constructor(
    public reportesService: ReportesService,
    public formGroup: FormBuilder,
    public sucursalServices: SucursalService,
    public resolucionesService: ResolucionesService,
    public formBuilder: FormBuilder,
    public utilidadesService: UtilidadesService,
    public modalService: BsModalService,
  ) {
    this.validacionesFormularioConsulta();
    this.ObtenerPermisos("resoluciones");
  }

  ngOnInit() {
    this.ObtenerListaSucursales();
    this.obtenerListaCompanias();
    this.obtenerListaTiposPolizas();

  }

  //metodo que mostrara la tabla de las resoluciones
  public verTablaResoluciones(form: NgForm): void {
    this.sucursal = form.value.lSucursales;
    this.compania = form.value.lCompanias;
    this.producto = form.value.lProductos;
    this.botonConsultaPresionado = true;
    this.resolucionesService.obtenerResolucionFacturas(this.sucursal, this.compania, this.producto).subscribe(
      (resp: ResolucionFacturas[]) => {
        this.listaResolucionFacturas = resp;
        this.mostrarTablaResoluciones = true;
      });
  }

  public eliminarResolucionFutura(resolucion: ResolucionFacturas): void {
    this.resolucionesService.eliminarResolucionFacturas(resolucion).subscribe(
      (resp: Resultado) => {
        this.respuestaEliminacion = resp;
        if (this.respuestaEliminacion.succes) {
          Swal.fire("Trabajo satisfactorio", this.respuestaEliminacion.message, "success");
          this.listaResolucionFacturas = this.listaResolucionFacturas.filter(factura => factura != resolucion);
        } else {
          Swal.fire("Ha ocurrido un error", this.respuestaEliminacion.message, "warning");
        }
      }
    );
  }

  // --------------------------- funciones para modal

  public crearResolucionFacturas() {
    let factura: ResolucionFacturas = this.armarObjetoResolucion();
    let initialState = {
      titulo: "Crear Resolucion Facturas",
      accion: "crear",
      factura: factura,
      facturas: this.listaResolucionFacturas
    };
    this.bsModalRef = this.modalService.show(ModalCreaEditaResolucionFacturaComponent, { initialState, class: 'modal-lg', backdrop: 'static' });
    this.bsModalRef.content.closeBtnName = 'Close';
    this.bsModalRef.content.event.subscribe((res: boolean) => {
      if(res){
        this.resolucionesService.obtenerResolucionFacturas(this.sucursal, this.compania, this.producto).subscribe(
          (resp: ResolucionFacturas[]) => {
            this.listaResolucionFacturas = resp;
            this.mostrarTablaResoluciones = true;
          });
      }
    });
  }

  public editarResolucionFacturas(factura: ResolucionFacturas) {
    factura.fechaInicial = factura.fechaInicial.replace("/", "-").replace("/", "-");
    factura.fechaFinal = factura.fechaFinal.replace("/", "-").replace("/", "-");
    let initialState = {
      titulo: `Editar Resolución N° ${factura.numeroResolucion}`,
      accion: "editar",
      factura: factura,
      facturas: this.listaResolucionFacturas
    };
    this.bsModalRef = this.modalService.show(ModalCreaEditaResolucionFacturaComponent, { initialState, class: 'modal-lg', backdrop: 'static' });
    this.bsModalRef.content.closeBtnName = 'Close';
    this.bsModalRef.content.event.subscribe((res: boolean) => {
      if(res){
        this.resolucionesService.obtenerResolucionFacturas(this.sucursal, this.compania, this.producto).subscribe(
          (resp: ResolucionFacturas[]) => {
            this.listaResolucionFacturas = resp;
            this.mostrarTablaResoluciones = true;
          });
      }
    });
  }

  private armarObjetoResolucion(): ResolucionFacturas {
    let resolucion = new ResolucionFacturas();
    resolucion.llave = 0;
    resolucion.compania = this.compania;
    resolucion.sucursal = this.sucursal;
    resolucion.producto = this.producto;
    resolucion.vigencia = true;
    resolucion.contador = 0;
    resolucion.estado = null;

    return resolucion;
  }

  // ------------------------  funciones para el buen funcionamiento del componente

  //metodo que tare un listado de las sucursales
  public ObtenerListaSucursales(): void { // lista  todas las sucursales
    this.reportesService.listaSucursales().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.listaSucursales = resp.resultado;
    });
  }

  //metodo que trae un lisatdod e las companias
  public obtenerListaCompanias(): void {
    this.sucursalServices.obtenerListaCompanias().subscribe((res: any) => {
      this.listaCompanias = res.resultado;
    });
  }

  public obtenerListaTiposPolizas(): void {
    this.sucursalServices.obtenerListaTiposPoliza().subscribe((res: any) => {
      this.productos = res.resultado;
    });
  }

  //metodo que valida el fromulario de los select de sucursales y companias
  private validacionesFormularioConsulta(): void {
    this.formConsulta = this.formBuilder.group({
      lCompanias: this.formGroup.control(0, [Validators.required, selectInicial]),
      lSucursales: this.formGroup.control(0, [Validators.required, selectInicial]),
      lProductos: this.formGroup.control(0, [Validators.required, selectInicial])
    });
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
      });
    });
  }

}
