import { Poliza } from "./poliza";

export class InformacionGeneral {
  public idDocumento: number;
  public tipo: string;
  public numeroDocumento: string;
  public vbaImpuesto: number;
  public baseImponible: number;
  public valorTributo: number;
  public total: number;
  public polizas: Poliza[];
}
