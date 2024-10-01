import { Component, OnInit } from '@angular/core';
import { EtlService } from 'src/app/services/etl.service';
import Swal from "sweetalert2";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public horas: Array<string> = [];
  public esEditar: boolean;
  constructor(private service: EtlService) { }

  //fecha constantes para las ejecuciones de etl
  private date = '01/01/24';

  ngOnInit() {
    this.horas = [];
    this.cargarHoras();
    this.ObtenerPermisos("horas-etl");
  }

  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que tenga
    const objsesion = JSON.parse(localStorage.getItem('sesion'));
    const menu = objsesion.listMenu;
    menu.forEach(item => { // Mapeo de los modulos y sus respectivos permisos
      item.menuHijos.map(alias => {
        if( alias.menuAlias === menuAlias){
          this.esEditar = alias.modificar;
        }
       else if (item.menuAlias === menuAlias ) {
          this.esEditar = item.modificar;
        }
      })
    });
  }

  cargarHoras(){
    this.service.obtenerHoras().subscribe(
      {
        next: (resp: any) => {
           this.horas = resp.resultado;
        },
        error: (err: any) => {
          console.log("error");
          console.log(err);
        }
      }
    );
  }
  habilitarHora(hora: any): boolean{
  if(hora.estado=='INA'){
    return true;
  }else{
    return false;
  }
  }
  
  cambiarEstado(hora: any, estado: string): void{
    hora.estado = estado;
  }
  orden():void {
    let listaOrden: Array<any> =this.horas;
    let horaAnterior=new Date;
    let error: any = 0;
    let horaDoce: any = 0;
    let contador: any = 0;
    for(let i = 0; i < listaOrden.length; i++){
      const hora =new Date('01/01/24 '+listaOrden[i].hora);
      if(listaOrden[i].hora=='00:00'){
        horaDoce=horaDoce+1;
      }
      if(listaOrden[i].estado=='ACT'){
        if(contador==0){
          horaAnterior=hora;
        }else{
          if(horaAnterior>=hora){
            error=error+1;
          }
          horaAnterior=hora;
        }
        contador=contador+1;
      }
    }
    if(error==0 && horaDoce==0){
      this.preguntar();
    }else{
      Swal.fire({
        type: "warning",
        title: "Ha ocurrido un error",
        text: "Error en el orden de las horas ACTIVAS, deben estar de manera ascendente y no deben repetirse",
      });
    }
  }
  preguntar():void {
    Swal.fire({
      title: "Desea guardar el horario?",
      type: "info",
      showCancelButton: true,
      showCloseButton: true,
      confirmButtonColor: "#3599dc",
      confirmButtonText: "Guardar",
      cancelButtonColor: "#8b0000",
      cancelButtonText: "Cancelar",
    }).then((result: any) => {
      if(result.dismiss == "close"){
         return;
      }
      if (result.value) {
        this.guardar();
      }
    });
  }

  guardar():void{
    let listaEnvio: Array<any> = this.horas.filter((d: any) => d.estado == 'ACT');
    if(listaEnvio.length > 1){
      listaEnvio.sort((a,b) => a.dni - b.dni);
      let envio: Array<any> = [];
      for(let i = 0; i < listaEnvio.length; i++){
        const timeInicial = listaEnvio[i].hora.split(" ");
        if (i==0){
          const objectGeneric = {
            ejecucion: `${this.date} 00:00:00`,
            inicio: `${this.date} 00:00:00`,
            fin: `${this.date} ${timeInicial}:00`,
            dni: 0,
            orden:0,
            hora: null,
            estado: "ACT"
          }
          envio.push(objectGeneric);
        }
        let timeFinal: Array<any> = [];
        let orden = i + 1;
        if(i == listaEnvio.length - 1){
          orden = 8;
          timeFinal = ['23:59', 'PM']
        } else {
          timeFinal = listaEnvio[i+1].hora.split(" ");
        }
        const object = {
          ejecucion: `${this.date} ${timeInicial[0]}:00`,
          inicio: `${this.date} ${timeInicial[0]}:00`,
          fin: `${this.date} ${timeFinal[0]}:00`,
          dni: listaEnvio[i].dni,
          orden,
          hora: listaEnvio[i].hora,
          estado: listaEnvio[i].estado
        }
        envio.push(object);
      }
      envio = [...envio, ...this.horas.filter((d: any) => d.estado == 'INA')]

      this.consumirServicioModificar(envio);
    } else {
      Swal.fire({
        type: "warning",
        title: "Advertencia",
        text: "Se requiere de al menos dos horarios en activo",
      });
    }
  }

  consumirServicioModificar(envio: any): void{
    this.service.modificarHorario(envio).subscribe({
      next: (resp: any) => {
          Swal.fire({
            type: "success",
            title: "Operación Exitosa",
            text: "Registro guardado",
          });
          this.ngOnInit();
      },
      error: (err: any) => {
        if(err.status = '400'){
          Swal.fire({
            type: "warning",
            title: "Advertencia",
            text: `No es posible inactivar la hora ${err.error.resultado}`,
          });
        } else {
          console.log("error");
          console.log(err);
          Swal.fire({
            type: "error",
            title: "Ha ocurrido un error",
            text: "Error",
          });
        }

      }
    });
  }

}
