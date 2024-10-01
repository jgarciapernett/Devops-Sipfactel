import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { poliza } from '../models/Polizas/polizas';
import { factura } from '../models/Polizas/detalleFactura';
import { Categoria } from '../models/Polizas/categoria';

@Injectable({
  providedIn: 'root'
})
export class ErroresService {

  public urlBase ='';

  constructor(
    private http: HttpClient
    ) {
    this.urlBase = environment.urlBaseServicio;
  }
  
    obtenerPolizas() {
      const url = `${this.urlBase}/polizas/listarTablaPrincipal`;
      return this.http.get(url);
  
    }
    obtenerNumPolizas(categoria:number, polizas:string) {
      
      const url = `${this.urlBase}/polizas/FiltroPolizaErrorEtl?categoria=${categoria}&numpoliza=${polizas}`;
      return this.http.get(url);
  
    }
    obtenerNumFac(fac:string) {
      const url = `${this.urlBase}/polizas/detalleFacturaEtl?idtabla=1&numpoliza=1&tabla=1`;
      return this.http.get(url);
  
    }
    obtenerErrores() {
      const url = `${this.urlBase}/polizas/FiltroPolizaErrorEtl`;
      return this.http.get(url);
  
    }
    obtenerNumfacturas(categoria:number,facturas:string) {
      const url = `${this.urlBase}/polizas/FiltroPolizaErrorEtl?categoria=${categoria}&numdoc=${facturas}`;
      return this.http.get(url);
  
    }
     obtenerTodosParametros(categoria:number,facturas:string,pol:string) {
      const url = `${this.urlBase}/polizas/FiltroPolizaErrorEtl?categoria=${categoria}&numdoc=${facturas}&numpoliza=${pol}`;
      return this.http.get(url);
  
    }
    obtenerCat(categoria:number) {
      const url = `${this.urlBase}/polizas/FiltroPolizaErrorEtl?categoria=${categoria}`;
      return this.http.get(url);
  
    }
   
   
    transaccion(numDoc:string,numpol) {
     
      const url = `${this.urlBase}/polizas/detalleTransaccion?numdoc=${numDoc}&numpoliza=${numpol}`;
      return this.http.get(url);
  
    }
    adquiriente(pol:string,mov:string) {
      
      const url = `${this.urlBase}/polizas/detalleAdquirientes?TipoMovimiento=${mov}&numpoliza=${pol}`;
      return this.http.get(url);
  
    }
    porcentajes() {
     
      const url = `${this.urlBase}/adquirientes/Porcentajes`;
      return this.http.get(url);
  
    }
    categorias() {
     
      const url = `${this.urlBase}/adquirientes/Categorias`;
      return this.http.get(url);
  
    }
    factura(id:number,poliza:string,tabla:string) {
      
      const url = `${this.urlBase}/polizas/detalleFacturaEtl?idtabla=${id}&numpoliza=${poliza}&tabla=${tabla}`;
      return this.http.get(url);
  
    }
    
  reenvio(numpoliza: string, tipo:string) {
    const url = `${this.urlBase}/polizas/reenviar?numdoc=${numpoliza}&tipodoc=${tipo}`;
    return this.http.get(url);
  }

    editarPoliza(factura: factura) {
      
      const httpOptions = new HttpHeaders().append(
        "Content-Type",
        "application/json; charset=UTF-8"
      );
      const url = `${this.urlBase}/polizas/editarDetalleErrorEtl`;
      return this.http.put(url, factura, { headers: httpOptions });
    }
    


    remediar(iderror: number , errorid:number , idtabla:number , tabla: string) {
      const httpOptions = new HttpHeaders().append(
        "Content-Type",
        "application/json; charset=UTF-8"
      );
      const url = `${this.urlBase}/Error?errorid=${iderror}&iderror=${errorid}&idtabla=${idtabla}&tabla=${tabla}`;
      return this.http.post(url, { headers: httpOptions });
    }
    
    categorizar(Categoria: number , iderror:number , obsv:string) {
    console.log('categorizacion',JSON.stringify(Categoria))
      const httpOptions = new HttpHeaders().append(
        "Content-Type",
        "application/json; charset=UTF-8"
      );
      const url = `${this.urlBase}/Error/categoria?categoria=${Categoria}&iderror=${iderror}&observacion=${obsv}`;
      return this.http.post(url, { headers: httpOptions });
    }
    }
    
    
    
   