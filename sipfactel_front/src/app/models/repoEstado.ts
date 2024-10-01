import { NgbDate } from '@ng-bootstrap/ng-bootstrap';
export class repoEstado {

  compania: string;
  estado: number;
  fechaTransaccion: string;
  hora: string;
  numPoliza: string;
  numTransaccion: string;
  tipoTransaccion: string;
  sucursal: string;

  constructor() {
    this.compania = '';
    this.estado = 0;
    this.fechaTransaccion = '';
    this.hora ='';
    this.numPoliza = '';
    this.tipoTransaccion = '';
    this.numTransaccion = '';
    this.sucursal = '';

  }
}
