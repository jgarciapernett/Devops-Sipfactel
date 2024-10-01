import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//#region components
import { MainComponent } from './components/reporte-adquiriente/main/main.component';
import { HomeComponent } from './components/reporte-adquiriente/home/home.component';
//#endregion components

//#region guards
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
//#endregion guards


const REPORTEADQUIRIENTESROUTES: Routes = [{
  path: 'reporteAdquiriente', component: MainComponent,
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
    { path: 'home', component: HomeComponent, data: { vista: 'reporteAdquirientes' } }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(REPORTEADQUIRIENTESROUTES)],
  exports: [RouterModule]
})
export class ReporteAquirienteRoutingModule { }