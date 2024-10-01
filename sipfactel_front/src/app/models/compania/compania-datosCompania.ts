import { Lista } from '../lista';
export class DatosCompania {
  public obligacionesfiscales: ( Lista )[] = [];
  public codascele: string;
  public codigopais: number;
  public codpoint: string;
  public documento: string;
  public nombre: string;
  public tipodocumento: number;
  public tipopersona: number;
  public razonsocial: string;
  public comp:number;
  public nombrepais?: string;

  constructor() {
    this.codascele = "";
    this.codigopais = 0;
    this.codpoint = "";
    this.documento = "";
    this.nombre = "";
    this.nombrepais = "";
    this.razonsocial = "";
    this.tipodocumento = 0;
    this.tipopersona = 0;
    this.comp = 0;
  }
}
