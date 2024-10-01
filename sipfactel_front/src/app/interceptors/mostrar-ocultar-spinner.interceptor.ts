import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpEventType
} from '@angular/common/http';

//#region services
// import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { NgxSpinnerService } from 'ngx-spinner';
//#endregion services

//#region bibliotecas
import { Observable, throwError } from 'rxjs';
import { map, finalize, catchError } from 'rxjs/operators';
import * as _ from 'lodash';
//#endregion bibliotecas


@Injectable({
  providedIn: 'root'
})
export class MostrarOcultarSpinnerInterceptor implements HttpInterceptor {
  // tslint:disable: variable-name
  private __reqsEnProgreso: HttpRequest<any>[] = [];

  constructor(
    private __spinnerService: NgxSpinnerService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      map((event: HttpEvent<any>) => {
        if (event.type === HttpEventType.Sent) {
          this.agregarRequestEnProgreso(req);
          this.mostrarSpinner();
        } else if (event.type === HttpEventType.Response) {
          this.eliminarRequestEnProgreso(req);
          this.ocultarSpinner();
        }
        return event;
      }),
      catchError((error: any) => {
        this.__spinnerService.hide();
        return throwError(error);
      })
    );
  }

  mostrarSpinner() {
    if (this.__reqsEnProgreso.length === 1) {
      this.__spinnerService.show();
    }
  }

  ocultarSpinner() {
    if (this.__reqsEnProgreso.length <= 0) {
      this.__spinnerService.hide();
    }
  }

  agregarRequestEnProgreso(req: HttpRequest<any>) {
    this.__reqsEnProgreso.push(req);
  }

  eliminarRequestEnProgreso(req: HttpRequest<any>) {
    const indiceReq = this.__reqsEnProgreso.indexOf(req);

    if (indiceReq >= 0) {
      this.__reqsEnProgreso.splice(indiceReq, 1);
    }
  }
}
