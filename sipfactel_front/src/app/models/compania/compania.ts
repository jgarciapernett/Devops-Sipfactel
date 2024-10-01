import { Lista } from '../lista';

export class Compania {
  public nombre: string;
  public codascele: string;
  public tipodocumento: number;
  public codigopais: number;
  public obligacionesfiscales: ( Lista )[] = [];
  public razonsocial: string;
  public codpoint: string;
  public documento: string;
  public nombrepais: string;
  public tipopersona: number;
  public facelectronica: number;
  public notacredito: string;
  public notadebito: string;
  public cantidad: string;
  public codempresa: string;
  public codestandar: number;
  public codatributo: number;
  public nomatributo: string;
  public porcatributo: number; 
  public formapago: number;
  public metodopago: number;
  public undmedida: number;                
  public notacreh: string;
  public regimen: number;
  public comp ?:number;
  public notadebh: string;


  constructor() {
    this.comp = 0;
    this.codascele = "";
    this.codigopais = 0;
    this.codpoint = "";
    this.documento = "";
    this.nombre = "";
    this.nombrepais = "";
    this.razonsocial = "";
    this.tipodocumento = 0;
    this.tipopersona = 0;
    this.facelectronica = 0;
    this.notacredito = "";
    this.notadebito = "";
    this.cantidad = "";
    this.codatributo = 0;
    this.codempresa = "";
    this.codestandar = 0;
    this.formapago = 0;
    this.metodopago = 0;
    this.nomatributo = "";
    this.porcatributo = 0;
    this.undmedida = 0;
    this.notadebh = "";
    this.notacreh = "";
    this.regimen = 0;
  }
}
