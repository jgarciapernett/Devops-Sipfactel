import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { selectInicial } from 'src/app/directives/validators/select.validator';
import { SucursalService } from '../../../services/sucursal.service';
import { Lista } from 'src/app/models/lista';
import { Router, ActivatedRoute } from '@angular/router';
import { sucursales } from 'src/app/models/sucursales/sucursales';
import { codigoPostal } from 'src/app/models/sucursales/codigoPostal';
import { NgbDate, NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { UtilidadesService } from 'src/app/services/utilidades.service';

@Component({
  selector: 'app-formulario-agregar',
  templateUrl: './formulario-agregar.component.html',
  styleUrls: ['./formulario-agregar.component.css']
})
export class FormularioAgregarComponent implements OnInit {

  @Input() id: number;
  public valor: string;
  public titulo: string;



  public frmAgregarSucursal: FormGroup;
  public listaDepartamentos: Lista[] = []
  public listaCiudades: Lista[] = []
  public listaCodigoPostal: codigoPostal[] = []
  public listaCompanias: Lista[];

  public nombreSucu: string
  public compania: number


  public formularioUno: boolean
  public fechaFin: NgbDate;
  public fechaInicial: NgbDate;


  public sucursales: sucursales


  public activarNotaDebito: boolean;
  public activarNotaCredito: boolean;

  constructor(
    public formBuilder: FormBuilder,
    public sucursalServices: SucursalService,
    private activedRoute: ActivatedRoute,
    public steps: NgbProgressbarConfig,


    public utilidadesService: UtilidadesService,

    private router: Router,

  ) {
    this.activarNotaCredito = true;
    this.activarNotaDebito = true;
    this.sucursales = new sucursales
    this.eventosIniciales()


  }


  eventosIniciales() {
    this.steps.max = 2;
    this.steps.striped = true;
    this.steps.animated = true;
    this.steps.type = "success";
    this.steps.height = "20px";
    this.valor = "1";
    this.titulo = "Agregar Sucursal"

  }

  ngOnInit() {
    this.validacionesSucursal()
    this.obtenerCodigoListaDepartamentos()
    this.obtenerCodigoListaCiudades()
    this.obtenerCodigoListaPostal()
    this.obtenerListaCompanias()
    // this.validacion()
    this.formularioUno = true
  }
  obtenerListaCompanias() {
    this.sucursalServices.obtenerListaCompanias().subscribe((res: any) => {
      this.listaCompanias = res.resultado;
    });
  }
  obtenerCodigoListaDepartamentos() {
    this.sucursalServices.obtenerDepartamento().subscribe((res: any) => {
      this.listaDepartamentos = res.resultado;
    })
  }
  obtenerCodigoListaCiudades() {
    this.sucursalServices.obtenerCiudades().subscribe((res: any) => {
      this.listaCiudades = res.resultado;
    })
  }

  obtenerCodigoListaPostal() {
    this.sucursalServices.obtenerCodigoPostal().subscribe((res: any) => {
      this.listaCodigoPostal = res.resultado;
    })
  }

  botonCancelar() {
    this.router.navigate(['/Sucursales/home'])

  }

  botonAtras() {
    this.formularioUno = true
    this.valor = "1"
    this.titulo = "Agregar Sucursal"
  }

  guardarSucursal() {
    this.sucursalServices.insertarSucursales(this.sucursales).subscribe(
      (res: any) => {

        Swal.fire({
          type: "success",
          title: "Operación Exitosa",
          text: "Registro guardado correctamente"
        });
        this.router.navigate(['/Sucursales/home'])

      },
      (error: HttpErrorResponse) => {

        this.router.navigate(['/Sucursales/home'])


      }
    );
  }

  obtenerDatosResolucion() {

    if (this.compania != 0) {
      this.listaCompanias.forEach((item) => {
        if (this.compania == item.id) {
          this.nombreSucu = item.nombre;
        }
      });
    }
  }
  validacionesSucursal() {
    this.frmAgregarSucursal = this.formBuilder.group({
      txtdAscel: this.formBuilder.control('', [Validators.required]),
      txtnombreSucursal: this.formBuilder.control('', [Validators.required]),
      ddlCodDepart: this.formBuilder.control(0, [Validators.required, selectInicial]),
      ddlCodCiudades: this.formBuilder.control(0, [Validators.required, selectInicial]),
      txtcodPoint: this.formBuilder.control('', [Validators.required]),
      txtDireccion: this.formBuilder.control('', [Validators.required]),
      ddlCodPostal: this.formBuilder.control(0, [Validators.required, selectInicial])
    });
  }
}
