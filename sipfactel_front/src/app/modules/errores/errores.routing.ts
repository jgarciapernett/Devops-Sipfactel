//#region components
import { MainComponent } from './main/main.component';
import { HomeComponent } from './home/home.component';
import { FormularioEditarComponent } from './formulario-editar/formulario-editar.component';

//#endregion components

//#region modulos
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
//#endregion modulos

const ErrorRoutes: Routes = [
  {
    path: 'Errores',
    component: MainComponent,
    canActivate: [
      LoginGuard,
      AuthGuard
    ],
    canActivateChild: [
      LoginGuard,
      AuthGuard
    ],

    children: [


        { path: 'editar/:idtbl/:numPoliza/:tbl/:tipoMov', component: FormularioEditarComponent , data : { vista: 'errores'}},
        { path: 'editar/:numPoliza/:tipoMov', component: FormularioEditarComponent , data : { vista: 'errores'}},
        { path: '', redirectTo: 'home', pathMatch: 'full' },
        { path: 'home', component: HomeComponent, data : { vista: 'errores'} }
      ]
    }
  ];

@NgModule({
  imports: [RouterModule.forChild(ErrorRoutes)],
  exports: [RouterModule]
})
export class ErroresRoutingModule {}
