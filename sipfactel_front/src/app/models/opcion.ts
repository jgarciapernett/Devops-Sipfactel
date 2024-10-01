export class opcion {
  public id: number;
  public nombre: string;
  public sistema: string;
  public estado:string
  public padre: number;
  public codigo: string;
  constructor() {
    this.id = 0;
    this.codigo= "";
    this.estado = undefined;
    this.sistema = "";
    this.padre = 0;
  }
}
