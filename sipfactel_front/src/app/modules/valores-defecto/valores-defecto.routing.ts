// module
import { NgModule} from '@angular/core';

// rutas
import { Routes, RouterModule } from '@angular/router';


//#region components
import { MainComponent } from './main/main.component' ;
import { HomeComponent } from './home/home.component';
  
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';


//#endregion components

const AdquirientesRoutes: Routes = [
  {
    path: 'Valores',
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
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent, data : { vista: 'valores'}},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(AdquirientesRoutes)],
  exports: [RouterModule]
})
export class ValoresRoutingModule {}


