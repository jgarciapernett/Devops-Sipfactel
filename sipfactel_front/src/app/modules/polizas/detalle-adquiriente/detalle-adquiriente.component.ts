import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormGroup, FormBuilder, Validators, FormControl } from "@angular/forms";
import { Lista } from "src/app/models/lista";
import { AdquirientesService } from "src/app/services/adquirientes.service";
import { codigoPostal } from "src/app/models/sucursales/codigoPostal";
import Swal from "sweetalert2";

@Component({
  selector: "app-detalle-adquiriente",
  templateUrl: "./detalle-adquiriente.component.html",
  styleUrls: ["./detalle-adquiriente.component.css"],
})
export class DetalleAdquirienteComponent implements OnInit {
  @Input() dniDocumento: number;
  @Input() tipo: string;
  @Input() dniAdquiriente: number;
  @Output() next = new EventEmitter<any>();
  @Output() back = new EventEmitter<any>();

  public form: FormGroup;
  public listaobligacionesFiscales: Lista[];
  public esObligacionValido: boolean = false;
  public listaobligacionesFiscalesSeleccionadas: Lista[];
  public listaPaises: Lista[];
  public listaCiudades: Lista[];
  public listaDepartamentos: Lista[];
  public codPostal: codigoPostal[];
  public listaFacElectronica: Lista[] = [];
  public Tributo: Lista[] = [];
  public regimenes: Lista[];

  constructor(
    private formBuilder: FormBuilder,
    private service: AdquirientesService
  ) {}

  ngOnInit() {
    this.createForm();
    this.obtenerObligacionFiscal();
    this.getPaises();
    this.getCiudades();
    this.getDepartamentos();
    this.getCodigosPostal();
    this.getFacturasElectronicas();
    this.getTributos();
    this.getRegimenes();
    this.cargarAdquirienteEditar();
  }

  cambioPais(): void {
    const pais = this.listaPaises.find((pais) => pais.id == this.form.value.pais);
    if (pais.nombre == "Colombia") {
      this.form.get("ciudad").enable();
      this.form.get("departamento").enable();
      this.form.get("codigoPostal").enable();
    } else {
      this.form.get("ciudad").disable();
      this.form.get("departamento").disable();
      this.form.get("codigoPostal").disable();
      this.form.patchValue({
        ciudad: null,
        departamento: null,
        codigoPostal: null
      })
    }
  }

  createForm() {
    this.form = this.formBuilder.group({
      tipoPersona: this.formBuilder.control({ value: "", disabled: true }),
      razonSocial: this.formBuilder.control(
        { value: "Error", disabled: false },
        [Validators.required]
      ),
      nit: this.formBuilder.control({ value: "", disabled: true }),
      tipoDocumento: this.formBuilder.control({ value: "", disabled: true }),
      obligacionesfiscales: this.formBuilder.control([]),
      pais: this.formBuilder.control("", [Validators.required]),
      ciudad: this.formBuilder.control("", [Validators.required]),
      departamento: this.formBuilder.control("", [Validators.required]),
      direccion: this.formBuilder.control("", [Validators.required]),
      codigoPostal: this.formBuilder.control("", [Validators.required]),
      nombreContacto: this.formBuilder.control("", [Validators.required]),
      correo: this.formBuilder.control("", [Validators.required]),
      telefono: this.formBuilder.control("", [Validators.required]),
      areaPertenece: this.formBuilder.control("", [Validators.required]),
      facturadorElectronico: this.formBuilder.control("", [Validators.required]),
      autorizaEntregaEmail: this.formBuilder.control("", [Validators.required] ),
      recibeXml: this.formBuilder.control("", [Validators.required]),
      deshabilitado: this.formBuilder.control("", [Validators.required]),
      tributo: this.formBuilder.control("", [Validators.required]),
      regimen: this.formBuilder.control("", [Validators.required]),
    });
  }

  cargarAdquirienteEditar() {
    this.service.obtenerOpcionListaPorId(this.dniAdquiriente).subscribe({
      next: (resp: any) => {
        this.form.patchValue(resp.resultado);
        this.esObligacionValido =
          this.form.value.obligacionesfiscales.length != 0;
        this.listaobligacionesFiscalesSeleccionadas =
          this.form.value.obligacionesfiscales;
      },
      error: (err: any) => {},
    });
  }

  obtenerObligacionFiscal() {
    this.service.listaObligacionesFiscales().subscribe((res: any) => {
      this.listaobligacionesFiscales = res.resultado;
    });
  }

  esObligacionSeleccionada(rolId: string) {
    // Verifica los permisos seleccionados

    const esSeleccionado = this.form.value.obligacionesfiscales.some(
      (role: Lista) => {
        return role.id.toString() === rolId.toString();
      }
    );
    return esSeleccionado;
  }

  Obligaciones(event: Event, idMenu: number, menuMenu: string, idstr: string) {
    // función de los checkbox
    const eventTarget = event.target as HTMLInputElement;
    if (eventTarget.checked) {
      // Valida que la informacion del checkbox obtenida tenga los mismos paramtros del modelo
      this.listaobligacionesFiscalesSeleccionadas.push(
        new Lista(idMenu, menuMenu, idstr)
      );
    } else {
      this.listaobligacionesFiscalesSeleccionadas =
        this.listaobligacionesFiscalesSeleccionadas.filter((role: Lista) => {
          // Elimina el array cuando se quita un check
          return role.id.toString() !== idMenu.toString();
        });
    }
    this.comprobarValideObligacion();
  }

  comprobarValideObligacion() {
    // Valida que minimo un permiso este seleccionado
    this.esObligacionValido =
      this.listaobligacionesFiscalesSeleccionadas.length > 0;
  }

  getPaises() {
    this.service.obtenerListaPaises().subscribe((resp) => {
      this.listaPaises = resp;
    });
  }

  getCiudades() {
    this.service.obtenerListaCiudades().subscribe((resp: any) => {
      this.listaCiudades = resp.resultado;
    });
  }

  getDepartamentos() {
    this.service.obtenerListaDepartamentos().subscribe((resp: any) => {
      this.listaDepartamentos = resp.resultado;
    });
  }

  getCodigosPostal() {
    this.service.obtenerCodigoPostal().subscribe((res: any) => {
      this.codPostal = res.resultado;
    });
  }

  getFacturasElectronicas() {
    this.service.obtenerFacturaElectronica().subscribe((resp: any) => {
      this.listaFacElectronica = resp.resultado;
    });
  }

  getTributos() {
    this.service.obtenerTributo().subscribe((resp: any) => {
      this.Tributo = resp.resultado;
    });
  }

  getRegimenes() {
    this.service.obtenerRegimenFiscal().subscribe((res: any) => {
      this.regimenes = res.resultado;
    });
  }

  preguntar(): void{
    this.form.controls["obligacionesfiscales"].setValue(this.listaobligacionesFiscalesSeleccionadas);
    if(!this.form.valid || this.form.value.obligacionesfiscales.length == 0){
      Swal.fire({
        type: "warning",
        title: "Formulario invalido"
      });
      Object.keys(this.form.controls).forEach((field) => {
        const control = this.form.get(field);
        if (control instanceof FormControl) {
          control.markAsTouched({ onlySelf: true });
        }
      });
      return;
    }

    Swal.fire({
      title: "Desea Habilitar el envio del documento?",
      type: "info",
      showCancelButton: true,
      showCloseButton: true,
      confirmButtonColor: "#3599dc",
      confirmButtonText: "Reenviar",
      cancelButtonColor: "#45dc35",
      cancelButtonText: "Guardar",
    }).then((result: any) => {
      if(result.dismiss == "close"){
        return;
     }
      if (result.value) {
        this.guardar(true);
      } else {
        this.guardar(false);
      }
    });
  }

  guardar(reenvio: boolean): void{
     const data = this.form.value;
     data.reenviar = reenvio;
     data.dni = this.dniAdquiriente;
     this.service.editarAdquirientes(data, this.dniDocumento, this.tipo).subscribe((resp: any) => {
      if(resp.codigoEstado = "200"){
        Swal.fire({
          type: "success",
          title: resp.resultado
        });
      }
    });
  }

  siguiente(): void {
    this.next.emit();
  }

  atras(): void {
    this.back.emit();
  }

}
