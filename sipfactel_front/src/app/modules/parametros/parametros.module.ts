import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastrModule } from 'ngx-toastr';

// modulos externos
import { DirectivesModule } from '../directives/directives.module';
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";


import { BrowserAnimationsModule } from "@angular/platform-browser/animations";


//#region rutas
import { ParametrosRoutingModule } from './parametros.routing';
import { RouterModule } from "@angular/router";
//#endregion rutas

// modulos internos
import { TablasModule } from '../tablas/tablas.module';
import { HttpClientModule } from "@angular/common/http";

// componentes
import { HomeComponent } from './home/home.component';
import { MainComponent } from './main/main.component';
import { DatosGeneralesComponent } from './datos-generales/datos-generales.component';
import { DatosProveedorComponent } from './datos-proveedor/datos-proveedor.component';

// servicios
import { parametrosService } from '../../services/parametros.service';




@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    DatosGeneralesComponent,
    DatosProveedorComponent],
  imports: [
    CommonModule,
    ParametrosRoutingModule,
    ToastrModule.forRoot(),
    FormsModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    TablasModule,
    BrowserAnimationsModule,
    DirectivesModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})

  ], providers:[
    parametrosService,
    Ng4LoadingSpinnerService
  ]
})
export class ParametrosModule { }
