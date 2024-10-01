import { Component, OnInit } from '@angular/core';
import { ReportesService } from 'src/app/services/reportes.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(public reportesService: ReportesService) { }

  ngOnInit() {
  }

  descargarAdquirientes():void { 
    this.reportesService.descargarExcelAdquirientes().subscribe((res: Blob) => {
      if(res.size > 0){
        saveAs(res, 'ReporteAdquirientes' + '.' + 'xlsx');
        this.reportesService.cantidadFaltanteAdquirientes().subscribe((response: number) => {
          if(response == 0){
            Swal.fire('Descarga completa', "Se han descargado todos los adquirientes disponibles", 'success');
          } else {
          Swal.fire('Descarga completa', `Hacen falta descargar un total de ${response.toString()} adquirientes luego de realizar el proceso de remediación`, 'success');
          } 
        } );
      } else {
        Swal.fire('Sin información', `No existe información a remediar`, 'warning');
      }
        
      });
      
  }

}