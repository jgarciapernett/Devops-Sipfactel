//#region components
import { MainComponent } from './main/main.component';
import { HomeComponent } from './home/home.component';
import { EditarRolesComponent } from './editar-roles/editar-roles.component';
import { AgregarRolesComponent } from './agregar-roles/agregar-roles.component';


//#endregion components

//#region modulos
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { LoginGuard } from 'src/app/guards/login.guard';
//#endregion modulos

const RolesRoutes: Routes = [
  {
    path: 'Roles',
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
      { path: 'editar/:roleRole', component: EditarRolesComponent, data: {vista: 'roles'} },
      { path: 'agregar', component: AgregarRolesComponent, data: {vista: 'roles'} },
      { path: 'home', component: HomeComponent , data: {vista: 'roles'} },
      { path: '**', redirectTo: 'home', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(RolesRoutes)],
  exports: [RouterModule]
})
export class RolesRoutingModule {}
