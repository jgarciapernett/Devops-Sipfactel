import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Components
import { HomeComponent } from './components/informe-contable/home/home.component';
import { MainComponent } from './components/informe-contable/main/main.component';
// import { ModalEditarComponent } from './components/modal-editar/modal-editar.component';

// // Módulos externos
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
// import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


// // Módulos internos
// import { DirectivesModule } from '../directives/directives.module';
// import {EjemploRoutingModule } from './ejemplos.routing';
// import { TablasModule } from '../tablas/tablas.module';

// Services
import { EjemplosService } from '../../services/ejemplos.service';
import { InformesRoutingModule } from './informe.routing';
import { DirectivesModule } from '../directives/directives.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TablasModule } from '../tablas/tablas.module';

@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    // ModalEditarComponent
  ],
  imports: [
 
  CommonModule,
    InformesRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    DirectivesModule,
    NgbModule,
    ReactiveFormsModule,
    TablasModule
  ],
  providers: [
    EjemplosService,
    Ng4LoadingSpinnerService,
  ]
})
export class InformeModule { }
