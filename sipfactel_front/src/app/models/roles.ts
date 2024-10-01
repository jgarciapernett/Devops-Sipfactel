import { Lista } from './lista';
import { menus } from './menusRol';
// tslint:disable-next-line: class-name
export class roles {
    public modulos: menus [] ;
    public modulosStr: string;
    public roleRole: number;
    public roleNombre: string;
    public roleDescripcion: string;
    public roleEsta: string;
    public roleUcreacion: string;
    public roleFcreacion: string;
    public roleFactualizacion: string;
    public roleConsultar: number;
    public roleCrear: number;
    public categoria:number ;
    public categoriaID:string ;
    public roleModificar: number;
    public roleUactualizacion: string;
    public agrego:boolean;

    constructor() {
      this.agrego = true;
      this.modulos ;
      this.modulosStr = "";
      this.roleRole = 1;
      this.roleNombre = '';
      this.categoriaID = ''
      this.roleDescripcion = '';
      this.roleEsta = '';
      this.roleUcreacion = 'string';
      this.roleFcreacion = '2020-04-13T13:29:16.137Z';
      this.roleFactualizacion = '2020-04-13T13:29:16.137Z ';
      this.roleConsultar = 1;
      this.roleCrear = 1;
      this.roleModificar = 1;
      this.categoria= 0
      this.roleUactualizacion = 'string'
    }
  }
