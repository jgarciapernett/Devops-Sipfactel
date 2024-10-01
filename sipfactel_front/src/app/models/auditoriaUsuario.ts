import { NgbDate } from '@ng-bootstrap/ng-bootstrap';

export class auditoria{
   public audiAudi: number;
   public audiDescripcion: string;
   public audiFecha: string;
   public audiUsuario: string;
   public audiFuncionalidad: string;
   public audiDetalle : string;
   public audiIp: string;
   public audiNombre: string;
   public audiRol: string;
constructor(){
   this.audiAudi = 0;
   this.audiDescripcion = '';
   this.audiFecha = '';
   this.audiUsuario = '';
   this.audiFuncionalidad = '';
   this.audiIp  = '';
   this.audiNombre = '';
   this.audiDetalle = '';
   this.audiRol = '';

}
}