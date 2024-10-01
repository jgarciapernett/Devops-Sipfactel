import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SimpleChanges
} from "@angular/core";
import { NgForm, FormGroup, FormBuilder, Validators } from "@angular/forms";

//#region servicios
import { UtilidadesService } from "../../../../services/utilidades.service";
import { CompaniasService } from "../../../../services/companias.service";
//#endregion

//#region modelos
import { tipoOperacion } from "src/app/models/compania/compania-tipoOperacion";
import { DatosCompania } from "src/app/models/compania/compania-datosCompania";
import { Lista } from 'src/app/models/lista';
//#endregion

//#region rutas
import { Compania } from "src/app/models/compania/compania";
import { selectInicial } from 'src/app/directives/validators/select.validator';
//#endregion

@Component({
  selector: "compania-tipo-operacion",
  templateUrl: "./tipo-operacion.component.html",
  styleUrls: ["./tipo-operacion.component.css"]
})
export class TipoOperacionComponent implements OnInit {
  @Input() companiaTipoEditar: Compania;
  @Output() tipo = new EventEmitter<Object>();
  @Output() oblig = new EventEmitter<boolean>();
  @Output() paso = new EventEmitter<number>();

  public ngAgregarCompania: NgForm;
  public frmAgregarTipoOperacion: FormGroup;
  public operacion: tipoOperacion;
  public datosCompania: DatosCompania;
  public pasoDePagina: Number;
  public listaFacturaElectronica: Lista[];
  public Obligacion : boolean

  constructor(
    public formBuilder: FormBuilder,
    public companiaService: CompaniasService,
    public utilidadesService: UtilidadesService
  ) {
    this.operacion = new tipoOperacion();
    this.Obligacion = true
  }

  ngOnInit() {
    this.pasoDePagina = 2;
    this.validacionesFormulario();
    this.obtenerFacturaElectronica();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.companiaTipoEditar.currentValue !== undefined) {
      if (changes.companiaTipoEditar.currentValue.comp !== undefined) {
        this.AsignacionCompaniaATipoOperacion(
          changes.companiaTipoEditar.currentValue
        );
      }
    }
  }

  AsignacionCompaniaATipoOperacion(objCompania: Compania) {
    this.operacion = Object.assign(new tipoOperacion(), objCompania);
  }

  agregarTipoOperacion() {
    var objRespuesta = new Object({ paso: 3, tipoOperacion: this.operacion });
    this.tipo.emit(objRespuesta);
  }

  volverAtras() {
    
    var objRespuesta = new Object({ paso: 1, tipoOperacion: this.operacion , oblig: true });
    this.tipo.emit(objRespuesta);
  }

  obtenerFacturaElectronica(){
    this.companiaService.listaFacturaElectronica().subscribe( (res: any ) =>{
      this.listaFacturaElectronica = res.resultado;
    });
  }

  validacionesFormulario() {
    this.frmAgregarTipoOperacion = this.formBuilder.group({
      ddlFacElectronica: this.formBuilder.control(0, [Validators.required,
        selectInicial]),
      txtNotaDebito: this.formBuilder.control("", [Validators.required]),
      txtNotaCredito: this.formBuilder.control("", [Validators.required]),
      txtNotaDebitoHuerfano: this.formBuilder.control("", [
        Validators.required
      ]),
      txtNotaCreditoHuerfano: this.formBuilder.control("", [
        Validators.required
      ])
    });
  }
}
