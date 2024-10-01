import { NgModule } from '@angular/core';

//#region componentes
import { HomeComponent } from './components/home/home.component';
import { MainComponent } from './components/main/main.component';
import { AgregarUsuarioComponent } from './components/agregar-usuario/agregar-usuario.component';
import { EditarUsuarioComponent } from './components/editar-usuario/editar-usuario.component';
//#endregion

//#region modulos internos
import {UsuariosRoutingModule } from './usuarios.routing';
// Módulos internos
import { DirectivesModule } from '../directives/directives.module';
import { TablasModule } from '../tablas/tablas.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
//#endregion

//#region modulos externos
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
//#endregion

//#region servicios
import { UsuariosService } from 'src/app/services/usuarios.service';
//#endregion

@NgModule({
  declarations: [HomeComponent, 
                 MainComponent, 
                 AgregarUsuarioComponent, 
                 EditarUsuarioComponent
  ],
 imports: [
    CommonModule,
    UsuariosRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    DirectivesModule,
    NgbModule,
    ReactiveFormsModule,
    TablasModule
  ],
  providers: [
    UsuariosService,
    Ng4LoadingSpinnerService,
  ]
})
export class UsuariosModule { }
