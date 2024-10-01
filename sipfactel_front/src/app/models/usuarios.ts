export class Usuarios {
    public nombreRol: string;
    public usuaEstado: number; 
    public usuaContrasenia: string;
    public usuaCorreo: string;
    public usuaRol: number;
    public usuaUsua: number;
    public usuaApellidos: string;
    public usuaNombres: string;
    public usuaUsuario: string;
    public esAgregar: boolean;
  
    constructor() {
      this.usuaEstado = 0;
      this.usuaCorreo = "";
      this.usuaRol = 0;
      this.usuaApellidos = "";
      this.usuaNombres = "";
      this.usuaUsua = 0;
      this.usuaUsuario = "";
      this.usuaContrasenia = "Periferia2020";
      this.esAgregar = true;
    }
  }
  
