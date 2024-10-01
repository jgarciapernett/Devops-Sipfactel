import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Components
import { HomeComponent } from './components/reporte-adquiriente/home/home.component';
import { MainComponent } from './components/reporte-adquiriente/main/main.component';

// // Módulos externos
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

// Services
import { ReporteAquirienteRoutingModule } from './reporteAdquiriente.routing';
import { DirectivesModule } from '../directives/directives.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
  ],
  imports: [
    CommonModule,
    ReporteAquirienteRoutingModule,
    BrowserAnimationsModule,
    DirectivesModule,
    NgbModule,
  ],
  providers: [
    Ng4LoadingSpinnerService,
  ]
})
export class ReporteAdquirientesModule { }
