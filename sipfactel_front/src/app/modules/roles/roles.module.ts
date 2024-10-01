import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { TablasModule } from '../tablas/tablas.module';
import { DirectivesModule } from '../directives/directives.module';
import { HttpClientModule } from '@angular/common/http';

import { HomeComponent } from './home/home.component';
import { MainComponent } from './main/main.component';


import { RolesRoutingModule } from './roles.routing';

import { RolesService } from '../../services/roles.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AgregarRolesComponent } from './agregar-roles/agregar-roles.component';
import { EditarRolesComponent } from './editar-roles/editar-roles.component';


@NgModule({
  declarations: [HomeComponent, MainComponent, AgregarRolesComponent, EditarRolesComponent],
  imports: [
    BrowserModule, FormsModule, NgbModule,
    NgbModule,
    CommonModule,
    TablasModule,
    NgbModule,
    TablasModule,
    DirectivesModule,
    HttpClientModule,
    ReactiveFormsModule, CommonModule,
    RolesRoutingModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'}),

  ],
  providers: [
    RolesService, Ng4LoadingSpinnerService,
  ],
})
export class RolesModule { }
