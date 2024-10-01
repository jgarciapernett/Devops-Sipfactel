import { Lista } from '../models/lista';
import { menus } from './menusRol';
import { permisos } from './permisos';
// tslint:disable-next-limenusne: class-name
export class agregar {
    public modulos: ( menus )[] = [];
    public roleNombre: string;
    public roleDescripcion: string;
    public roleEsta: string;
    public roleRole:number
    public categoria:number

    constructor() {
      this.roleNombre = '';
      this.roleDescripcion = '';
      this.roleEsta = '';
      this.categoria = 0

    }
  }