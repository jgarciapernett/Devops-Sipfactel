import { Lista } from '../lista';

export class Adquiriente {
  public obligacionesfiscales: ( Lista )[] = [];
  public areapertenece: string;
  public codPostal: number;  
  public codciudad: number;
  public codtipotributo: number;
  public coddepartamento: number;
  public codenviofe: number;
  public codpais: number;
  public codtipoidentificacion: number;
  public codtipopersona: number;
  public codRegimen: number;
  public contacto: string;
  public correo: string;
  public deshabilitado: number;
  public direccion: string;
  public direccionfiscal: string;
  public dv: string;
  public facturadorelectronico: number;
  public fechaactualizacion: string;
  public fechainsercion: string;
  public nombrecomercial: string;
  public nombrerazonsocial: string;
  public numidentificacion: string;
  public per: number;
  public recibirxml: number;
  public telefono: any;
  public usuario: string;



  constructor(
  ) {
    this.areapertenece = '';
    this.codciudad = 0;
    this.coddepartamento = 0;
    this.codenviofe = 0;
    this.codpais = 0;
    this.codtipoidentificacion = 0;
    this.codtipopersona = 0;
    this.codPostal = 0;
    this.codRegimen = 0;
    this.contacto = '';
    this.correo = '';
    this.deshabilitado = 0;
    this.direccion = '';
    this.direccionfiscal = '';
    this.fechaactualizacion = '';
    this.fechainsercion = '';
    this.nombrecomercial = '';
    this.nombrerazonsocial = '';
    this.numidentificacion = '';
    this.per = 0;
    this.codtipotributo = 0 ;
    this.recibirxml = 0;
    this.telefono = 0;
    this.usuario = '';
  }
}
