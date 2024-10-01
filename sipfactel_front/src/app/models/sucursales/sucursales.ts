import { sucursal } from '../desplegableSucu';
export class sucursales {
  public sucu?: number;
  public codascele: string;
  public codciudad: number;
  public coddepart: number;
  public codpostal: number;
  public codpoint: string;
  public nomsuc: string
  public direccion: string;
 


  constructor() {
    this.codascele = "";
    this.codpoint = "";
    this.direccion = "";
    this.codciudad = 0;
    this.nomsuc = "";
    this.codpostal = 0;
    this.coddepart = 0;

  
  }
}

