import { Component,
  OnInit,
  Output,
  EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

// Servicios

import { UtilidadesService } from '../../../services/utilidades.service';
import { parametrosService } from 'src/app/services/parametros.service';

// Rutas
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';


// models
import { parametrosProveedor } from '../../../models/parametros';
import { Lista } from 'src/app/models/lista';

@Component({
  selector: 'app-datos-proveedor',
  templateUrl: './datos-proveedor.component.html'
})
export class DatosProveedorComponent implements OnInit {

  @Output() proveedor = new EventEmitter<Object>();

  public frmDatosProveedor: FormGroup;
  public parametrosProveedorObj: parametrosProveedor;
 public Agrego: boolean;
 public resultado:string;
 public paises: Lista[];

  constructor(
    private rutaActiva: ActivatedRoute,
    private router: Router,
    public parametroPService: parametrosService,
    public formBuilder: FormBuilder,
    public utilidadesService: UtilidadesService,

  ) {

    this.listaPais()
  }
  ngOnInit() {
    this.Agrego = true;
    this.parametrosProveedorObj = new parametrosProveedor();
    this.parametrosProveedorObj.codPais = undefined;
    this.validacionesParametros();
    this.traerDatosProveedor();
    
  }

   agregarDatosProveedor() {// Evento que muestra el componente

    let objRespuesta = new Object({
      paso: 3, parametrosProveedor : this.parametrosProveedorObj,
    });
    this.proveedor.emit(objRespuesta);
  }

 traerDatosProveedor(){ // Lista en el formulario el ultimo registro agregado
   this.parametroPService.obtenerDatosProveedor().subscribe((res : any)=> { // Solicitud con exitó o fallo
    this.resultado = JSON.stringify(res);
    this.parametrosProveedorObj = res.resultado;
   })
 }

 listaPais() { // Lista todos los paises en un seleccionable

  this.parametroPService.listaPaises().subscribe((resp: any) => { // Solicitud con exitó o fallo
    this.paises = resp.resultado;
    
  });
}

  validacionesParametros() { // validacion de los campos requeridos del formulario y su longitud
    this.frmDatosProveedor = this.formBuilder.group({
      txtCodigoPais: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtNitProveedor: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtDvProveedor: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtSofwareID: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtSofwareSecurityCode: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtNitDIAN: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtDigitoDIAN: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtQRCode: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtVersionUBL: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtVersionFormato: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtCUFE: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)]),
      txtCantidadLineas: this.formBuilder.control('', [Validators.required, Validators.maxLength(50)])


    });
  }
}
