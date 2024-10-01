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

const PolizasRoutes: Routes = [
  {
    path: 'Polizas',
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


        // { path: 'editar/:numFac/:numPoliza/:tipoMov', component: FormularioEditarComponent , data : { vista: 'polizas'}},
        { path: 'editar/:dni/:tipo/:adquiriente', component: FormularioEditarComponent , data : { vista: 'polizas'}},
        { path: '', redirectTo: 'home', pathMatch: 'full' },
        { path: 'home', component: HomeComponent, data : { vista: 'polizas'} }
      ]
    }
  ];

@NgModule({
  imports: [RouterModule.forChild(PolizasRoutes)],
  exports: [RouterModule]
})
export class PolizasRoutingModule {}
