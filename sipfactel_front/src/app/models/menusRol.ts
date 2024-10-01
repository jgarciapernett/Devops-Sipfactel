import { menusHijos } from './menusHijosRoles';

export class menus {
    constructor(
      public hijos?: menusHijos [] ,
      public idMenu?: number, 
      public menuMenu? :string,
      public modificar?: number,
      public consultar?: number,
      public crear?: number,


    ) { 
      this.idMenu = 0
      this.menuMenu = ''
      this.modificar = 0
      this.consultar= 0
      this.crear = 0
      this.hijos = []
    }
  }
  