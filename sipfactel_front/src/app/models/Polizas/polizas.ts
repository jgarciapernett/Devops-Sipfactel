export class poliza {

constructor(
  public tabla: string,
  public idtabla: number,
  public observacion: string,
  public resultado: poliza[] ,
  public categoria: string ,
  public errormensaje: string,
  public errorid: string,
  public fechaenvio: string,
  public fechaemision: string,
  public id:string,
  public tipoMovimiento: string,
  public numdocumento: string,
  public numpoliza: string,
  public valor: string,
) {
  this.tipoMovimiento = "",
  this.errormensaje = '',
  this.errorid ='' ,
  this.fechaenvio = '',
  this.fechaemision = '',
  this.numdocumento ='',
 this.numpoliza = '',
 this.valor = '',
 this.tabla = '',
 this.observacion='',
 this.idtabla= 0
 

}
}