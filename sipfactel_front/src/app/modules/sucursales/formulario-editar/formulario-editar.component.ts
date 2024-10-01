import { sucursales } from 'src/app/models/sucursales/sucursales';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { selectInicial } from 'src/app/directives/validators/select.validator';
import { SucursalService } from '../../../services/sucursal.service';
import { Lista } from 'src/app/models/lista';
import { Router, ActivatedRoute } from '@angular/router';
import { codigoPostal } from 'src/app/models/sucursales/codigoPostal';
import { NgbDate, NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { UtilidadesService } from 'src/app/services/utilidades.service';

@Component({
  selector: 'app-formulario-editar',
  templateUrl: './formulario-editar.component.html',
  styleUrls: ['./formulario-editar.component.css']
})
export class FormularioEditarComponent implements OnInit {

  @Input() id :number;
  public sucu : number
  public comp : number
 public valor: string;
 public titulo: string;



  public frmAgregarSucursal: FormGroup;
  public listaDepartamentos : Lista [] = []
  public listaCiudades : Lista [] = []
  public listaCodigoPostal: codigoPostal []=[]
  public listaCompanias: Lista[];

  public nombreSucu:string
  public compania: number

  public frmtablaSucursal: FormGroup
  public fechaFin: NgbDate;
  public fechaInicial: NgbDate;


  public sucursales : sucursales


  public activarNotaDebito: boolean;
  public activarNotaCredito: boolean;

  constructor(
    public formBuilder: FormBuilder,
    public sucursalServices: SucursalService,
    private activedRoute : ActivatedRoute,
    public steps: NgbProgressbarConfig,
    public utilidadesService: UtilidadesService,
    private router: Router
  ) {

    this.activarNotaCredito = true;
    this.activarNotaDebito = true;
    this.sucursales = new sucursales
    this.eventosIniciales()


   }


  eventosIniciales(){

    this.steps.max = 2;
    this.steps.striped = true;
    this.steps.animated = true;
    this.steps.type = "success";
    this.steps.height = "20px";
    this.valor = "1";
    this.titulo = "Editar Sucursal"

  }


  ngOnInit() {

    this.validacionesSucursal()
    this.obtenerCodigoListaDepartamentos()
    this.obtenerCodigoListaCiudades()
    this.obtenerCodigoListaPostal()
    this.obtenerListaCompanias()
    this.obtenerSucursalPorId()

  }

  obtenerListaCompanias() {

    this.sucursalServices.obtenerListaCompanias().subscribe((res: any) => {
      this.listaCompanias = res.resultado;
      this.listaCompanias.forEach(
        item => this.comp = item.id
      )
    });

  }
  obtenerCodigoListaDepartamentos(){

    this.sucursalServices.obtenerDepartamento().subscribe((res: any) => {
      this.listaDepartamentos = res.resultado;
    });

  }
  obtenerCodigoListaCiudades(){

    this.sucursalServices.obtenerCiudades().subscribe((res: any) => {
      this.listaCiudades = res.resultado;
    });

  }

  obtenerCodigoListaPostal(){

    this.sucursalServices.obtenerCodigoPostal().subscribe((res: any) => {
      this.listaCodigoPostal = res.resultado;
    });

  }

  botonCancelar(){

    this.router.navigate(['/Sucursales/home'])

  }

  servicioEditar(){

    this.sucursalServices.editarLaSucursal(this.sucursales).subscribe(
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


  obtenerSucursalPorId() {

    this.id = Number(this.activedRoute.snapshot.params.id.toString());

    this.sucursalServices.obtenerInformacionDeSucursal(this.id).subscribe(
      (res: any) => {
       this.sucursales = res.resultado



      }
    );

  }

  validacionesSucursal() {

    this.frmAgregarSucursal = this.formBuilder.group({
      txtdAscel:this.formBuilder.control('', [Validators.required]),
      txtnombreSucursal:this.formBuilder.control('', [Validators.required]),
      ddlCodDepart:this.formBuilder.control(0, [Validators.required, selectInicial]),
      ddlCodCiudades:this.formBuilder.control(0, [Validators.required, selectInicial]),
      txtcodPoint:this.formBuilder.control('', [Validators.required]),
      txtDireccion:this.formBuilder.control('', [Validators.required]),
      ddlCodPostal:this.formBuilder.control(0, [Validators.required, selectInicial])
    });
  }

}
