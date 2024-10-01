import { Lista } from '../lista';

export class datosA {
  public areaPertenece : string
  public codCiudad : number
  public codDepartamento : number
    public codPais : number 
    public codRegimen : number 
    public codTipoTributo : number 
    public contacto: string;
    public correo: string;
    public direccionFiscal :string
    public direccionFisica : string
    public estado: string
    public facturadorElectronico : number
    public nombreComercial : string
    public obligacionesfiscales: ( Lista )[] = [];
    public nombreRazonSocial : string
    public post : number
    public telefono : number
    public vad : number
 


  constructor(
  ) {
this.areaPertenece = ''
this.obligacionesfiscales = []
this.codCiudad = 0
this.codDepartamento = 0
this.codPais = 0
this.codRegimen = 0
this.codTipoTributo = 0
this.contacto = ''
this.correo = ''
this.direccionFiscal = ''
this.direccionFisica = ''
this.estado = ''
this.facturadorElectronico = 0
this.nombreComercial = ''
this.nombreRazonSocial = ''
this.post = 0
this.telefono = 0
this.vad = 0
  }
}
