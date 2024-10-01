import { Injectable } from '@angular/core';
import { environment } from '../../../src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class ReportesService {
  public url: any;
  public urlBase = '';

  constructor(private http: HttpClient) {
    this.urlBase = environment.urlBaseServicio;
  }
  obtenerReporte(fechaFin: string, fechaIni: string, transa: number, numTransa: string, numPoliza: string, nombre: string) {

    switch (nombre) {
      case 'fechas':
        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}`;
        return this.http.get(this.url);
        ;
      case 'transaccion':

        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&tipodoc=${transa}&numdoc=${numTransa}`;
        return this.http.get(this.url);
        ;
      case 'tipoDoc':

        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&tipodoc=${transa}`;
        return this.http.get(this.url);
        ;
      case 'Doc':
        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}`;
        return this.http.get(this.url);

      case 'DocumentosN':
        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}`;
        return this.http.get(this.url, { responseType: 'blob' })

      case 'transaPoliza':
        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&tipodoc=${numTransa}`;
        return this.http.get(this.url, { responseType: 'blob' })

      case 'numP':
        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}`;
        return this.http.get(this.url)

      case 'todos':
        this.url = `${this.urlBase}/Reportes/FiltroReporteContable?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&tipodoc=${transa}&numdoc=${numTransa}`;
        return this.http.get(this.url);
    }


  }







  // tslint:disable-next-line: max-line-length
  obtenerReporteEstado(compania: string, estado: number, fechaFin: string, fechaIni: string, numTransa: string, numPoliza: string, sucursal: string, nombre: string) {
    
    switch (nombre) {
      case 'obligatorio':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}`;
        return this.http.get(this.url);
        break;
      case 'poliza':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}`;
        return this.http.get(this.url);
        break;
      case 'compania':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}`
        return this.http.get(this.url);
        break;
      case 'numTransa':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}`
        return this.http.get(this.url);
        break;
      case 'sucursal':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&sucursal=${sucursal}`
        return this.http.get(this.url);

        break;
      // --------------------
      case 'poliza&numTransa':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}&numpoliza=${numPoliza}`
        return this.http.get(this.url);

        break;

      case 'numTransa&compania':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}`
        return this.http.get(this.url);

        break;

        case 'numTransa&sucursal':
          this.url =
            `${this.urlBase}/Reportes/FiltroReporteEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}&sucursal=${sucursal}`
          return this.http.get(this.url);
  
          break;

        case 'compania&sucursal':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&sucursal=${sucursal}`
        return this.http.get(this.url);

        break;

      case 'todosCampos':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}&numpoliza=${numPoliza}&sucursal=${sucursal}`
        return this.http.get(this.url);

        break;

      case 'poliza&compania':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}`
        return this.http.get(this.url);
        break;

        case 'poliza&transaccion&compania':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}`
        return this.http.get(this.url);
        break;

        case 'poliza&sucursal&compania':
          this.url =
            `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&sucursal=${sucursal}`
          return this.http.get(this.url);
          break;

      case 'numTransa&compania&sucursal':
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}&sucursal=${sucursal}`
        return this.http.get(this.url);

        break;


      default:
        this.url =
          `${this.urlBase}/Reportes/FiltroReporteEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}&sucursal=${sucursal}`;
        return this.http.get(this.url);
        break;

    }
  }
  listaCompañinas() {
    const url = `${this.urlBase}/companias/opciones`;
    return this.http.get(url);

  }

  listaSucursales() {
    const url = `${this.urlBase}/adquirientes/Sucursales`;
    return this.http.get(url);

  }
  listatipoTransaccion() {
    const url = `${this.urlBase}/adquirientes/TipoDocumentos`;
    return this.http.get(url);

  }

  listaEstado() {
    const url = `${this.urlBase}/adquirientes/EstadoFactura`;
    return this.http.get(url);

  }

  descargarExcelReporte(compania: string, estado: number, fechaFin: string, fechaIni: string, numTransa: string, numPoliza: string, sucursal: string, nombre: string): Observable<Blob> {

    switch (nombre) {
      case 'obligatorio':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}`;
        return this.http.get(this.url, { responseType: 'blob' });
        break;
      case 'poliza':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}`;
        return this.http.get(this.url, { responseType: 'blob' });
        break;
      case 'compania':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}`
        return this.http.get(this.url, { responseType: 'blob' });
        break;
      case 'numTransa':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}`
        return this.http.get(this.url, { responseType: 'blob' });
        break;
      case 'sucursal':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&sucursal=${sucursal}`
        return this.http.get(this.url, { responseType: 'blob' });

        break;
      // --------------------
      case 'poliza&numTransa':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numTransa}&numpoliza=${numPoliza}`
        return this.http.get(this.url, { responseType: 'blob' });

        break;

      case 'poliza&compania':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}`
        return this.http.get(this.url, { responseType: 'blob' });

        break;

      case 'poliza&sucursal':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&sucursal=${sucursal}`
        return this.http.get(this.url, { responseType: 'blob' });
        break;
        

        case 'poliza&transaccion&compania':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}`
        return this.http.get(this.url, { responseType: 'blob' });
        break;

        case 'poliza&sucursal&compania':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&sucursal=${sucursal}`
        return this.http.get(this.url, { responseType: 'blob' });
        break;

        case 'compania&sucursal':
          this.url =
            `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&sucursal=${sucursal}`
          return this.http.get(this.url, { responseType: 'blob' });
          break;

      case 'todosCampos':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}&sucursal=${sucursal}`
        return this.http.get(this.url, { responseType: 'blob' });
        break;

      case 'numTransa&compania&sucursal':
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}&sucursal=${sucursal}`
        return this.http.get(this.url, { responseType: 'blob' });

      default:
        this.url =
          `${this.urlBase}/Reportes/GenerarExcelEstado?compania=${compania}&estado=${estado}&fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPoliza}&numdoc=${numTransa}&sucursal=${sucursal}`;
        return this.http.get(this.url, { responseType: 'blob' });
        break;

    }
  }

  descargarExcelInforme(fechaFin: string, fechaIni: string, id: string, numDoc: string, numPol: string, tipoDoc: number, nombre: string): Observable<Blob> {

    switch (nombre) {
      case 'fechas':
        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' });
        ;
      case 'transaccion':

        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&tipodoc=${tipoDoc}&numdoc=${numDoc}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' });
        ;
      case 'tipoDoc':

        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&tipodoc=${tipoDoc}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' });
        ;
      case 'Doc':
        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&numdoc=${numDoc}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' });

      case 'numP':
        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPol}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' })

      case 'DocumentosN':
        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPol}&numdoc=${numDoc}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' })

      case 'transaPoliza':
        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPol}&tipodoc=${tipoDoc}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' })

      case 'todos':
        this.url = `${this.urlBase}/Reportes/GenerarExcel?fechafin=${fechaFin}&fechaini=${fechaIni}&numpoliza=${numPol}&tipodoc=${tipoDoc}&numdoc=${numDoc}&compania=${id}`;
        return this.http.get(this.url, { responseType: 'blob' });
    }
  }

  public descargarExcelAdquirientes(): Observable<Blob>{
   this.url = `${this.urlBase}/Reportes/generar/excel/adquirientes`;
   return this.http.get(this.url, { responseType: 'blob' });
  }

  public cantidadFaltanteAdquirientes(): Observable<number>{
    this.url = `${this.urlBase}/Reportes/adquirientes/cantidad`;
   return this.http.get(this.url).pipe(map((response:any) => {
     return response.resultado;
   }));
  }

}
