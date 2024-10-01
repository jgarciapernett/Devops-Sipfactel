import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";


import { TablasModule } from "../tablas/tablas.module";
import { DirectivesModule } from "../directives/directives.module";
import { HttpClientModule } from "@angular/common/http";

import { HomeComponent } from './components/home/home.component';
import { MainComponent } from './components/main/main.component';

import { AuditoriaRoutingModule } from './auditoria.routing';




import { AuditoriaService } from '../../services/auditoria.service'
// import { } from ''; 
import { BusquedaUsuariosComponent } from './components/busqueda-usuarios/busqueda-usuarios.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';


@NgModule({
  declarations: [HomeComponent, MainComponent,BusquedaUsuariosComponent],
  imports: [
    BrowserModule, FormsModule, NgbModule,
    NgbModule,
    CommonModule,
    AuditoriaRoutingModule,
    TablasModule,
    NgbModule,
    TablasModule,
    DirectivesModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers:[
    AuditoriaService,Ng4LoadingSpinnerService,
  ],
 
})
export class AuditoriaModule{}
