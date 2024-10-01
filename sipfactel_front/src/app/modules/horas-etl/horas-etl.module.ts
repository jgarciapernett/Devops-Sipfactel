import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainComponent } from './main/main.component';
import { HomeComponent } from './home/home.component';
import { HorasEtlRoutingModule } from './horas-etl.routing';
import { RouterModule } from "@angular/router";
import { EtlService } from 'src/app/services/etl.service';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";



@NgModule({
  declarations: [
    MainComponent,
    HomeComponent],
  imports: [
    CommonModule,
    HorasEtlRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})
  ],
  providers: [
    EtlService
  ]
})
export class HorasEtlModule { }
