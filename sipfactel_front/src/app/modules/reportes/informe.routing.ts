import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//#region components
import { MainComponent } from './components/informe-contable/main/main.component';
import { HomeComponent } from './components/informe-contable/home/home.component';
//#endregion components

//#region guards
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
//#endregion guards


const ReportesRoutes: Routes = [{
  path: 'Informe', component: MainComponent,
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
    { path: 'home', component: HomeComponent, data: { vista: 'informe' } },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(ReportesRoutes)],
  exports: [RouterModule]
})
export class InformesRoutingModule { }