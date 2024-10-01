import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';



import { HomeComponent } from './home/home.component';
import { MainComponent } from './main/main.component';
import { FormularioEditarComponent } from './formulario-editar/formulario-editar.component';
import { TablasModule } from '../tablas/tablas.module';




import { DirectivesModule } from '../directives/directives.module';
import { ToastrModule } from 'ngx-toastr';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PaginacionService } from '../../services/paginacion.service';

import { PolizasService } from '../../services/polizas.service';
import { ErroresRoutingModule } from './errores.routing';


@NgModule({
  // tslint:disable-next-line: max-line-length
  declarations: [HomeComponent, MainComponent,  FormularioEditarComponent],
  imports: [
    TablasModule,
    CommonModule,
    ErroresRoutingModule,
    DirectivesModule,
    ToastrModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    BrowserAnimationsModule,
  ],
  providers: [
    PolizasService,Ng4LoadingSpinnerService,PaginacionService
  ]
})
export class ErroresModule { }
