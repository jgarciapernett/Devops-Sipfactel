import { NgModule } from '@angular/core';
import { ToastrModule } from 'ngx-toastr';
import { CommonModule } from '@angular/common';

//#region modulos externos
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//#endregion modulos externos

//#region modulos internos
import { TablasModule } from '../tablas/tablas.module';
import { DirectivesModule } from '../directives/directives.module';
import { HttpClientModule } from '@angular/common/http';
//#endregion modulos internos

//#region componentes
import { MainComponent } from './components/main/main.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { ModalAgregarComponent } from './components/modal-agregar/modal-agregar.component';
import { ModalEditarComponent } from './components/modal-editar/modal-editar.component';
//#endregion componentes

// #region Services
import { ConfiguracionService } from '../../services/configuracion.service';
// #endregion Services

// #region Rutas
import { ConfiguracionRoutingModule } from './configuracion.routing';
// #endregion Rutas

@NgModule({
  declarations: [MainComponent,
                 InicioComponent, 
                 ModalAgregarComponent, 
                 ModalEditarComponent ],
  imports: [
    CommonModule,
    FormsModule,
    ToastrModule.forRoot(),
    ConfiguracionRoutingModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    TablasModule, BrowserAnimationsModule,
    DirectivesModule,
  ],providers: [
    ConfiguracionService,
    Ng4LoadingSpinnerService
  ]
})
export class ConfiguracionModule { }
