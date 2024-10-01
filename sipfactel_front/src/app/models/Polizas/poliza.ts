import { Detalle } from "./detalle";

export class Poliza {
  public idPoliza: number;
  public poliza: string;
  public ramo: string;
  public prima: number;
  public detalle: Detalle[];
}
