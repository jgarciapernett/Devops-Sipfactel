import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';



import { HomeComponent } from './home/home.component';
import { MainComponent } from './main/main.component';
import { FormularioEditarComponent } from './formulario-editar/formulario-editar.component';
import { TablasModule } from '../tablas/tablas.module';




import { PolizasRoutingModule } from './polizas.routing';
import { DirectivesModule } from '../directives/directives.module';
import { ToastrModule } from 'ngx-toastr';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PaginacionService } from '../../services/paginacion.service';

import { PolizasService } from '../../services/polizas.service';

import { AccordionModule } from 'ngx-bootstrap/accordion';
import { DetalleTransaccionComponent } from './detalle-transaccion/detalle-transaccion.component';
import { DetalleAdquirienteComponent } from './detalle-adquiriente/detalle-adquiriente.component';
import { AdquirientesService } from 'src/app/services/adquirientes.service';


@NgModule({
  // tslint:disable-next-line: max-line-length
  declarations: [HomeComponent, MainComponent,  FormularioEditarComponent, DetalleTransaccionComponent, DetalleAdquirienteComponent],
  imports: [
    TablasModule,
    CommonModule,
    PolizasRoutingModule,
    DirectivesModule,
    ToastrModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    BrowserAnimationsModule,
    AccordionModule.forRoot()
  ],
  providers: [
    PolizasService,Ng4LoadingSpinnerService,PaginacionService,AdquirientesService
  ]
})
export class PolizasModule { }
