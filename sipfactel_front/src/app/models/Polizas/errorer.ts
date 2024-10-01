import { Categoria } from './categoria';
export class error     {


    constructor(
      public resultado: error[] ,
      public mensaje: string,
      public fechaemision: string,
      public fechainsercion: string,
      public iderror: number,
      public idtabla: number,
      public numpersona: string,
      public tabla: string,
      public tipomovimiento: string,
      public numdocumento: string,
      public numpoliza: string,
      public valor: string,
      public errorid: number,
      public categoria: number,
      
      public observacion: string ,
   
    ) {
      this.categoria = 0,   
      this.numpersona = '', 
      this.tipomovimiento = "",
      this.mensaje = '',
      this.fechainsercion = '',
      this.fechaemision = '',
      this.numdocumento ='',
      this.numpoliza = '',
      this.valor = '',
      this.errorid= 0,
      this.tabla = '',
      this.idtabla = 0
      this.iderror = 0
      this.observacion= ''
    
    }
    }