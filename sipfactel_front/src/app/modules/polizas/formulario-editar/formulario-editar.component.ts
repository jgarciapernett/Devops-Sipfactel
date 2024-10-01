import { Component, OnInit, Input } from "@angular/core";

//#region  modelos
import { adquiriente } from "../../../models/Polizas/adquirienteP";
import { factura } from "../../../models/Polizas/detalleFactura";
import { poliza } from "src/app/models/Polizas/polizas";
import { transaccion } from "src/app/models/Polizas/transaccion";

//#endregion servicios

//#region bibliotecas
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { NgbProgressbarConfig } from "@ng-bootstrap/ng-bootstrap";
import Swal from "sweetalert2";
import { HttpErrorResponse } from "@angular/common/http";
import { selectInicial } from "../../../directives/validators/select.validator";
import { PolizasService } from "src/app/services/polizas.service";
//#endregion bibliotecas
@Component({
  selector: "app-formulario-editar",
  templateUrl: "./formulario-editar.component.html",
})
export class FormularioEditarComponent implements OnInit {
  @Input() numFac: string;
  @Input() numPoliza: string;
  @Input() tipoMov: string;
  @Input() polizas: poliza[] = [];

  public tipo: string;
  public dni: number;
  public adquiriente: number;

  public info: any;

  public numPol: string;
  public numFact: string;

  public porcentajes: any[];
  public datosTransaccion: transaccion;
  public datosAdquiriente: adquiriente;
  public datosFactura: factura;
  public formCalculos: FormGroup;
  public resultado: string;

  public formularioTransaccion: boolean;
  public formularioAdquiriente: boolean;
  public formulariofactura: boolean;
  public formulariouno: boolean;
  public formulariodos: boolean;
  public formulariotres: boolean;

  constructor(
    public formBuilder: FormBuilder,
    private polizasService: PolizasService,
    private router: Router,
    public steps: NgbProgressbarConfig,
    private activedRoute: ActivatedRoute
  ) {
    this.validacionesFormulario(),
      (this.datosTransaccion = new transaccion()),
      (this.datosAdquiriente = new adquiriente()),
      (this.datosFactura = new factura()),
      (this.formularioTransaccion = true);
    this.formulariouno = true;
    this.formularioAdquiriente = false;
    this.formulariofactura = false;
  }

  ngOnInit() {
    this.tipo = this.activedRoute.snapshot.params.tipo;
    this.dni = this.activedRoute.snapshot.params.dni;
    this.adquiriente = this.activedRoute.snapshot.params.adquiriente;
    this.PorcentajeIva();
    this.detalleTransaccion();
  }

  detalleTransaccion() {
    this.formularioTransaccion = true;
    this.formularioAdquiriente = false;
    this.formulariofactura = false;
    this.formulariouno = true;
    this.formulariodos = false;
    this.formulariotres = false;
  }

  detalleAdquiriente() {
    this.formularioTransaccion = false;
    this.formularioAdquiriente = true;
    this.formulariofactura = false;
    this.formulariouno = false;
    this.formulariodos = true;
    this.formulariotres = false;
  }

  detalleFactura() {
    this.formularioTransaccion = false;
    this.formularioAdquiriente = false;
    this.formulariofactura = true;
    this.formulariouno = false;
    this.formulariodos = false;
    this.formulariotres = true;
    this.busquedaFactura();
  }

  siguienteAdquiriente() {
    if ((this.formularioTransaccion = true)) {
      this.formularioTransaccion = false;
      this.formularioAdquiriente = true;
      this.formulariofactura = false;
      this.formulariouno = false;
      this.formulariodos = true;
      this.formulariotres = false;
    }
  }
  siguienteFactura() {
    if ((this.formularioAdquiriente = true)) {
      this.formularioTransaccion = false;
      this.formularioAdquiriente = false;
      this.formulariofactura = true;
      this.formulariouno = false;
      this.formulariodos = false;
      this.formulariotres = true;
      this.busquedaFactura();
    }
  }

  atrasAdquiriente() {
    if ((this.formulariofactura = true)) {
      this.formularioTransaccion = false;
      this.formularioAdquiriente = true;
      this.formulariofactura = false;
      this.formulariouno = false;
      this.formulariodos = true;
      this.formulariotres = false;
    }
  }
  atrasTransaccion() {
    if ((this.formulariodos = true)) {
      this.formularioTransaccion = true;
      this.formularioAdquiriente = false;
      this.formulariofactura = false;
      this.formulariouno = true;
      this.formulariodos = false;
      this.formulariotres = false;
    }
  }

  busquedaFactura() {
    // Servicio lista la informacion del formulario detalle factura
    this.polizasService
      .factura(this.dni, this.tipo)
      .subscribe((resp: any) => {
        // Solicitud completada con éxito o fallo
        this.resultado = JSON.stringify(resp);
        this.info = resp.resultado;
      });
  }

  botonCancelar() {
    // Funcion de regreso a la pantalla home

    this.router.navigate(["/Polizas/home"]);
  }

  PorcentajeIva() {
    // Servicio que lista informacion dentro del desplegable tasa iva
    this.polizasService.obtenerTipoImpuesto("IVA").subscribe((resp: any) => {
      // Solicitud completada con éxito o fallo
      this.porcentajes = resp.resultado;
    });
  }

  preguntar(): void{
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
        this.editarDatos(true);
      } else {
        this.editarDatos(false);
      }
    });
  }

  editarDatos(reenviar: boolean) {
    this.info.reenviar = reenviar;
    this.polizasService.editarPoliza(this.info, this.tipo, this.adquiriente).subscribe((res: any) => {
      Swal.fire({
        type: "success",
        title: "Operación Exitosa",
        text: res.resultado,
      });
    }),
      (error: HttpErrorResponse) => {
        // Solicitud a fallado
      };
  }

  validacionesFormulario() {
    // Validacion de los campos requeridos del formulario detalle factura
    this.formCalculos = this.formBuilder.group({
      cantidad: this.formBuilder.control("", [Validators.required]),
      linea: this.formBuilder.control("", [Validators.required]),
      valor: this.formBuilder.control("", [Validators.required]),
      iva: this.formBuilder.control("", [Validators.required]),
      tasa: this.formBuilder.control(0, [Validators.required, selectInicial]),
      SubIva: this.formBuilder.control("", [Validators.required]),
      SubValor: this.formBuilder.control("", [Validators.required]),
      total: this.formBuilder.control("", [Validators.required]),
    });
  }

  concatenacionPoliza(data: any): string {
    return `${data.poliza} - ${data.ramo}`;
  }

  calcular(data: any) {
    this.calcularIva(this.info);
  }

  calculos(data: any) {
    data.prima = data.detalle.reduce(
      (prev, curr) => Number(prev) + Number(curr.prima),
      0
    );
    data.valorTributo = data.detalle.reduce(
      (prev, curr) => Number(prev) + Number(curr.valorTributo),
      0
    );
    const suma = this.info.polizas.reduce(
      (prev, curr) => Number(prev) + Number(curr.prima),
      0
    );
    this.info.vbaImpuesto = suma;
    this.info.baseImponible = suma;
    this.info.valorTributo = this.info.polizas.reduce(
      (prev, curr) => Number(prev) + Number(curr.valorTributo),
      0
    );
    this.info.total = suma + this.info.valorTributo;
  }

  /*calcula el iva, solo se calcula el iva de aquellas polizas que tengan solo un detalle, si tienen mas de uno es una mixta
    y en ese caso solo calcula el iva de la parte gravada*/
  calcularIva(data: any) {
    const iva = this.porcentajes.find((p) => p.id == data.idPorcentaje);
    data.porcentaje = iva.llave;
    for (let p of data.polizas) {
      const cantidad = p.detalle.lenght;
      for (let d of p.detalle) {
        if (cantidad > 1 && Number(d.valorTributo) == 0) {
          continue;
        }
        d.valorTributo = Math.floor((Number(d.prima) * iva.llave) / 100);
      }
    }
    for (let p of this.info.polizas) {
      this.calculos(p);
    }
  }

  comprueba(): boolean {
    for (let data of this.info.polizas) {
      for (let data2 of data.detalle) {
        if (data2.prima == null || Number(data2.prima) < 0) {
          return true;
        }
      }
    }
    return false;
  }
}
