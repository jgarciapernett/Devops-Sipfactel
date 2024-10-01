import {
  Component,
  OnInit,
  Output,
  EventEmitter
} from '@angular/core';
import { FormBuilder, FormGroup,  Validators } from '@angular/forms';

// serivicios

import { parametrosService } from '../../../services/parametros.service';
import { UtilidadesService } from '../../../services/utilidades.service';
import { ToastrService } from 'ngx-toastr';

// Rutas
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

// models
import { parametrosGenerales } from 'src/app/models/ParametrosGenerales';
import { Lista } from 'src/app/models/lista';

@Component({
  selector: 'app-datos-generales',
  templateUrl: './datos-generales.component.html'
})
export class DatosGeneralesComponent implements OnInit {
  
  @Output() dato = new EventEmitter<Object>();
  public moneda: Lista[];
  public pais: Lista[];
  public ambiente: Lista[];

  public Agrego: boolean;
  public frmDatosGenerales: FormGroup;
  public resultado : string;
  public parametrosGeneralesObj: parametrosGenerales;

  constructor(
    private rutaActiva: ActivatedRoute,
    private router: Router,
    public parametroGService: parametrosService,
    public formBuilder: FormBuilder,
    public utilidadesService: UtilidadesService,
    public toastr: ToastrService
  ) {

    this.listaAmbiente(),
    this.listaMoneda(),
    this.listaPais()
  }
  ngOnInit() {
    this.Agrego = true;
    this.validaciones();
    this.parametrosGeneralesObj = new parametrosGenerales();
    this.trarDatosGenerales();
    this.parametrosGeneralesObj.ambiente = undefined
    this.parametrosGeneralesObj.codPais = undefined
    this.parametrosGeneralesObj.nomPais = undefined
    this.parametrosGeneralesObj.moneda = undefined
   
  }


  agregaDatosGenerales() { // Evento para mostrar o ocultar el componente
    let objRespuesta = new Object({
      paso: 2,
      parametrosGenerales: this.parametrosGeneralesObj
    });
    this.dato.emit(objRespuesta);
  }

  listaAmbiente() { // lista  todos los ambientes en un seleccionable
    // tslint:disable-next-line: deprecation
    this.parametroGService.listaAmbiente().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.ambiente = resp.resultado;
   
    });
  }
  listaPais() { // Lista  todos los paises en un seleccionable
    // tslint:disable-next-line: deprecation
    this.parametroGService.listaPaises().subscribe((resp: any) => { // Solicitud con exitó o fallo
      this.pais = resp.resultado;
   
    });
  }
   listaMoneda() { // Lista los tipos de moneda en un seleccionable
  // tslint:disable-next-line: deprecation
  this.parametroGService.listaMonedas().subscribe((resp: any) => {// Solicitud con exitó o fallo
    this.moneda = resp.resultado;
 
  });
}
  trarDatosGenerales() { // Lista la informacion den el formulario del ultimo registro agregado 

     this.parametroGService.obtenerDatosGenerales().subscribe((res: any) => {// Solicitud con exitó o fallo
       this.resultado = JSON.stringify(res);
       this.parametrosGeneralesObj = res.resultado;
     });
 
   }
  validaciones() { // Validacion de los campos requeridos y la longitud dentro del formulario
    this.frmDatosGenerales = this.formBuilder.group({
     
      txtnombrePais: this.formBuilder.control('', [
        Validators.required,
        Validators.maxLength(50)
      ]),
      txtmoneda: this.formBuilder.control('', [
        Validators.required,
        Validators.maxLength(50)
      ]),
      txtambiente: this.formBuilder.control('', [
        Validators.required,
        Validators.maxLength(50)
      ])
    });
  }
}
