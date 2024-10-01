import { Component, OnInit, Input } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { NgbProgressbarConfig } from "@ng-bootstrap/ng-bootstrap";
import Swal from "sweetalert2";
import { parametrosService } from "../../../services/parametros.service";
import { Router, ActivatedRoute } from "@angular/router";
import { UtilidadesService } from "src/app/services/utilidades.service";

@Component({
  selector: "app-editar",
  templateUrl: "./editar.component.html",
  styleUrls: ["./editar.component.css"],
})
export class EditarComponent implements OnInit {
  public formulario: FormGroup;
  public parametro: any;

  constructor(
    public formBuilder: FormBuilder,
    public service: parametrosService,
    private activedRoute: ActivatedRoute,
    public steps: NgbProgressbarConfig,
    public utilidadesService: UtilidadesService,
    private router: Router
  ) {}

  ngOnInit() {
    this.eventosIniciales();
    this.validacionesFormulario();
    this.obtenerParametro();
  }

  eventosIniciales() {
    this.steps.max = 2;
    this.steps.striped = true;
    this.steps.animated = true;
    this.steps.type = "success";
    this.steps.height = "20px";
  }

  botonCancelar() {
    this.router.navigate(["/sistema/home"]);
  }

  servicioEditar() {
    this.service.actualizarParametro(this.parametro).subscribe(
      (res: any) => {
        Swal.fire({
          type: "success",
          title: "Operación Exitosa",
          text: "Registro guardado correctamente",
        });
        this.router.navigate(["/sistema/home"]);
      },
      (error: any) => {
        Swal.fire({
          type: "error",
          title: "Error en guardar",
          text: "Registro no guardado",
        });
        this.router.navigate(["/sistema/home"]);
      }
    );
  }

  obtenerParametro() {
    const id = Number(this.activedRoute.snapshot.params.id.toString());
    this.service.consultarParametroId(id).subscribe({
      next: (resp: any) => {
        this.parametro = resp.resultado;
      },
      error: (err: any) => {
        console.log("error al consultar parametro");
        console.log(err);
      },
    });
  }

  validacionesFormulario() {
    this.formulario = this.formBuilder.group({
      nombre: this.formBuilder.control("", [Validators.required]),
      descripcion: this.formBuilder.control("", [Validators.required]),
      valor: this.formBuilder.control(0, [Validators.required]),
    });
  }
}
