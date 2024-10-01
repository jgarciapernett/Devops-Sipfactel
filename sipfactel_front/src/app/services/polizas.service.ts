import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { poliza } from '../models/Polizas/polizas';
import { factura } from '../models/Polizas/detalleFactura';

@Injectable({
  providedIn: 'root'
})
export class PolizasService {

  public urlBase ='';

  constructor(
    private http: HttpClient
    ) {
    this.urlBase = environment.urlBaseServicio;
  }
  categorias() {

    const url = `${this.urlBase}/adquirientes/Categorias`;
    return this.http.get(url);

  }
    obtenerPolizas(poliza: string, documento: string, fIniEnvio: string, fFinEnvio: string,
      fIniEmision: string, fFinEmision: string, pagina: number, tamano: number) {

      let headers = new HttpHeaders();
      if(poliza) headers = headers.append("numpoliza", poliza);
      if(documento) headers = headers.append("numdoc", documento);
      if(fIniEnvio) headers = headers.append("fIniEnvio", fIniEnvio);
      if(fFinEnvio) headers = headers.append("fFinEnvio", fFinEnvio);
      if(fIniEmision) headers = headers.append("fIniEmision", fIniEmision);
      if(fFinEmision) headers = headers.append("fFinEmision", fFinEmision);
      headers = headers.append("pagina", String(pagina));
      headers = headers.append("tamano", String(tamano));
      const url = `${this.urlBase}/polizas/FacturaFiltro`;
      return this.http.get(url, { headers });
    }

    transaccion(dni: number,tipo: string) {
      const headers = new HttpHeaders()
      .append("dni", String(dni))
      .append("tipo", tipo);
      const url = `${this.urlBase}/polizas/detalleTransaccion`;
      return this.http.get(url, { headers });
    }
    adquiriente(pol:string,mov:string) {

      const url = `${this.urlBase}/polizas/detalleAdquirientes?TipoMovimiento=${mov}&numpoliza=${pol}`;
      return this.http.get(url);

    }
    porcentajes() {

      const url = `${this.urlBase}/adquirientes/Porcentajes`;
      return this.http.get(url);

    }

    obtenerTipoImpuesto(impuesto: string) {
      const url = `${this.urlBase}/adquirientes/impuesto?valor=${impuesto}`;
      return this.http.get(url);
    }

    factura(dni:number, tipo:string) {
      const url = `${this.urlBase}/polizas/detalleFactura?documento=${dni}&tipo=${tipo}`;
      return this.http.get(url);
    }

  reenvio(numDocumento: string, tipo:string) {
    const url = `${this.urlBase}/polizas/reenviar?numdoc=${numDocumento}&tipodoc=${tipo}`;
    return this.http.get(url);
  }

    editarPoliza(documento: any, tipo: string, adquiriente: number) {

      const httpOptions = new HttpHeaders().append(
        "Content-Type",
        "application/json; charset=UTF-8"
      )
      .append("adquiriente", String(adquiriente))
      .append("tipo", tipo);
      const url = `${this.urlBase}/polizas/editarDetalle`;
      return this.http.put(url, documento, { headers: httpOptions });
    }

    categorizar(Categoria: number , idtabla:number , obsv:string , tabla:string) {
      console.log('categorizacion',JSON.stringify(Categoria))
        const httpOptions = new HttpHeaders().append(
          "Content-Type",
          "application/json; charset=UTF-8"
        );
        const url = `${this.urlBase}/polizas/categoria?categoria=${Categoria}&idtabla=${idtabla}&observacion=${obsv}&tabla=${tabla}`;
        return this.http.post(url, { headers: httpOptions });
      }
    }



