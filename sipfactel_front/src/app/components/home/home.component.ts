import { Component, OnInit } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct, NgbDateNativeAdapter } from '@ng-bootstrap/ng-bootstrap';
import { fundido } from '../../animation/animation';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Subject } from 'rxjs';

declare var require: any;
require("bootstrap");
@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"],
  providers: [{ provide: NgbDateAdapter, useClass: NgbDateNativeAdapter }],
  animations: [fundido]
})
export class HomeComponent implements OnInit {
  public opcionesFiltro$: Subject<any[]> = new Subject<any[]>();
  public permisos: any[] = [];


  public urlBase: string;
  public model;
  public model1: Date;
  public model2: Date;
  Alerts: any
  constructor(
    private toastr: ToastrService,
    private spinnerService: Ng4LoadingSpinnerService,
    private router: Router
  ) {

    this.Alerts = JSON.parse(localStorage.getItem('alerts'))
  }

  ngOnInit() {

    this.permisos = this.Alerts.listAlerta
    const isLogeado = true;

    if (!isLogeado) {
      this.router.navigate(["/login"]);
    }
    /** spinner starts on init */
  }


  spiner() {
    this.spinnerService.show();

    setTimeout(() => {
      /** spinner ends after 5 seconds */

      this.spinnerService.hide();
    }, 5000);
  }



}
