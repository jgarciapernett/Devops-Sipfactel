import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainComponent } from './main/main.component';
import { HomeComponent } from './home/home.component';
import { parametrosService } from '../../services/parametros.service';
import { SistemaRoutingModule } from './sistema.routing';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { TablasModule } from '../tablas/tablas.module';
import { HttpClientModule } from "@angular/common/http";
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { EditarComponent } from './editar/editar.component';

@NgModule({
  declarations: [
    MainComponent,
    HomeComponent,
    EditarComponent],
  imports: [
    CommonModule,
    SistemaRoutingModule,
    ToastrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'}),
    RouterModule,
    TablasModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NgbModule
  ],
  providers: [
    parametrosService,
    Ng4LoadingSpinnerService
  ]
})
export class SistemaModule { }
