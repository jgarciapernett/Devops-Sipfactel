import { NgModule } from '@angular/core';
import { ToastrModule } from 'ngx-toastr';
import { CommonModule } from '@angular/common';

// modulos externos
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

//#region rutas
import { AdquirientesRoutingModule } from './adquiriente.routing';

import { RouterModule } from '@angular/router';
//#endregion rutas

// modulos internos

import { TablasModule } from '../tablas/tablas.module';
import { DirectivesModule } from '../directives/directives.module';
import { HttpClientModule } from '@angular/common/http';

// componentes
import { MainComponent } from './main/main.component' ;

import { HomeComponent } from './home/home.component';
import { EditarAdquirientesComponent } from './editar-adquirientes/editar-adquirientes.component';
import { AccordionModule } from 'ngx-bootstrap/accordion';


// servicios



@NgModule({
  declarations: [
    MainComponent,
    HomeComponent,
    EditarAdquirientesComponent,


  ],

  imports: [
    CommonModule,
    AdquirientesRoutingModule,
    ToastrModule.forRoot(),
    FormsModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    TablasModule,
    BrowserAnimationsModule,
    DirectivesModule,
    AccordionModule.forRoot(),
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})

  ],
  providers: [ Ng4LoadingSpinnerService]
})
export class AdquirientesModule {}
