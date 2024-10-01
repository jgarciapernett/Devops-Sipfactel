// module
import { NgModule} from '@angular/core';

// rutas
import { Routes, RouterModule } from '@angular/router';


//#region components
import { MainComponent } from './components/main/main.component' ;
import { InicioComponent } from './components/inicio/inicio.component';
import { ModalAgregarComponent } from './components/modal-agregar/modal-agregar.component';
import { ModalEditarComponent } from './components/modal-editar/modal-editar.component';
//#endregion components
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';

const HomologacionRoutes: Routes = [
  {
    path: 'Homologacion',
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
      { path: 'editar', component: ModalEditarComponent ,data : { vista: 'homologacion'}},
      { path: 'agregar', component: ModalAgregarComponent ,data : { vista: 'homologacion'}},
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: InicioComponent ,data : { vista: 'homologacion'}}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(HomologacionRoutes)],
  exports: [RouterModule]
})
export class HomologacionRoutingModule {}
