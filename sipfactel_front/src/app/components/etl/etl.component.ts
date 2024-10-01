import { Component, OnInit } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct, NgbDateNativeAdapter } from '@ng-bootstrap/ng-bootstrap';
import { fundido } from '../../animation/animation';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Subject } from 'rxjs';
import { EtlService } from 'src/app/services/etl.service';

@Component({
  selector: 'app-etl',
  templateUrl: './etl.component.html',
  styleUrls: ['./etl.component.css']
})
export class EtlComponent implements OnInit {

  public opcionesFiltro$: Subject<any[]> = new Subject<any[]>();
  public ejecuciones: any[] = [];

  public urlBase: string;
  public model;
  public model1: Date;
  public model2: Date;
  constructor(
    private toastr: ToastrService,
    private spinnerService: Ng4LoadingSpinnerService,
    private router: Router,
    private service: EtlService
  ) {

  }

  ngOnInit() {

    this.consultar();

    const isLogeado = true;

    if (!isLogeado) {
      this.router.navigate(["/login"]);
    }
    /** spinner starts on init */
  }

  consultar(){
    this.service.obtenerEjecuciones().subscribe(
      {
        next: (resp: any) => {
          this.ejecuciones = resp.resultado;
        },
        error: (err: any) => {
            console.log("Error al consultar etl");
            console.log(err);
        }
      }
    );
  }

  spiner() {
    this.spinnerService.show();

    setTimeout(() => {
      /** spinner ends after 5 seconds */

      this.spinnerService.hide();
    }, 5000);
  }

}
