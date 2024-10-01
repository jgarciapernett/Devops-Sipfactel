export class homologacion {
  public id?: number;
  public estado:string;
  public conf: number;
  public homologado: string;
  public descripcion: string;
  public factualiza?: string;
  public fcrea?: string;
  public fuente: number;
  public original: string;
  public uactualiza?: string;
  public ucrea?: string;
  public nomfuente:string

  
  constructor() {
    this.conf = 0;
    this.descripcion = '';
    this.factualiza = '';
    this.estado = undefined;
    this.fcrea = '';
    this.homologado = '';
    this.id = 0;
    this.original = '' ;
    this.uactualiza = '';
    this.ucrea = '';
    this.nomfuente = ''
  }
}
