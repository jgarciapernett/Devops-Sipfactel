import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//#region components
import { MainComponent } from './components/reporte-estado/main/main.component';
import { HomeComponent } from './components/reporte-estado/home/home.component';
//#endregion components

//#region guards
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
//#endregion guards


const ReportesRoutes: Routes = [{
  path: 'Reporte', component: MainComponent,
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
    { path: 'home', component: HomeComponent, data: { vista: 'reporte' } },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(ReportesRoutes)],
  exports: [RouterModule]
})
export class ReportesRoutingModule { }