import { NgModule} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component' ;
import { HomeComponent } from './home/home.component';
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { EditarComponent } from './editar/editar.component';


//#endregion components

const SistemaRoutes: Routes = [
  {
    path: 'sistema',
    component: MainComponent,
    canActivate: [
      LoginGuard,
      AuthGuard
    ],
    canActivateChild:[
      LoginGuard,
      AuthGuard
    ],
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent, data: { vista:'sistema'}},
      { path: "editar/:id", component: EditarComponent, data: { vista: 'sistema' } },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(SistemaRoutes)],
  exports: [RouterModule]
})
export class SistemaRoutingModule {}
