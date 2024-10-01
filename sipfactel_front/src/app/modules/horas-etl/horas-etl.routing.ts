import { NgModule} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component' ;
import { HomeComponent } from './home/home.component';
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';


//#endregion components

const HorasEtlRoutes: Routes = [
  {
    path: 'horas-etl',
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
      { path: 'home', component: HomeComponent, data: { vista:'sistema'}}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(HorasEtlRoutes)],
  exports: [RouterModule]
})
export class HorasEtlRoutingModule {}
