export class datosGenerales {
  public cantidad: string;
  public codatributo: number;
  public codempresa: string;
  public porcatributo: number;
  public formapago: number;
  public undmedida: number;
  public codestandar: number;
  public nomatributo: string;
  public metodopago: number;
  public regimen: number;

  constructor() {
    this.cantidad = "";
    this.codatributo = 0; 
    this.codempresa = "";
    this.codestandar = 0;
    this.formapago = 0;
    this.metodopago = 0;
    this.nomatributo = "";
    this.porcatributo = 0;
    this.undmedida = 0;
    this.regimen = 0;
  }
}
