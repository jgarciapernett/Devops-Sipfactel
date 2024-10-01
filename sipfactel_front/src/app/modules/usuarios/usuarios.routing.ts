import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//#region components
import { MainComponent } from './components/main/main.component';
import { HomeComponent } from './components/home/home.component';
import { AgregarUsuarioComponent } from './components/agregar-usuario/agregar-usuario.component';
import { EditarUsuarioComponent } from './components/editar-usuario/editar-usuario.component';
//#endregion components

//#region guards
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
//#endregion guards

//#region models
import { Vista } from 'src/app/models/vista';
//#endregion models

const vistas = {
  Usuarios: new Vista('Usuarios'),
};

const UsuariosRoutes: Routes = [{
  path: 'Usuarios', component: MainComponent,
  canActivate: [
    LoginGuard,
    AuthGuard
  ],
  canActivateChild: [
    LoginGuard,
    AuthGuard
  ],
  children: [
    { path: 'editar/:id', component: EditarUsuarioComponent, data: { vista: 'usuarios' } },
    { path: 'agregar', component: AgregarUsuarioComponent, data: { vista: 'usuarios' } },
    { path: 'home', component: HomeComponent, data: { vista: 'usuarios' } },
    { path: '', redirectTo: 'home', pathMatch: 'full' },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(UsuariosRoutes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
