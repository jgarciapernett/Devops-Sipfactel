import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

//#region components
import { MainComponent } from "./components/main/main.component";
import { HomeComponent } from "./components/home/home.component";
import { BusquedaUsuariosComponent } from './components/busqueda-usuarios/busqueda-usuarios.component';
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';

//#endregion components

//#region guard

//#endregion guards

//#region models

//#endregion models

const AuditoriaRoutes: Routes = [
  {
    path: "Auditoria", component: MainComponent,
    canActivate: [
      LoginGuard,
      AuthGuard
    ],
    canActivateChild: [
      LoginGuard,
      AuthGuard
    ],
    children: [
      {path : "buscar", component: BusquedaUsuariosComponent },
      { path: "**", redirectTo: "home", pathMatch: "full" },
      { path: "home", component: HomeComponent, data: { vista: 'auditoria'} }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(AuditoriaRoutes)],
  exports: [RouterModule]
})
export class AuditoriaRoutingModule {}
