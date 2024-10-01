//#region  module
import { NgModule } from "@angular/core";
//#endregion

//#region  rutas
import { Routes, RouterModule } from "@angular/router";
//#endregion

//#region components
import { MainComponent } from "./main/main.component";
import { HomeComponent } from "./home/home.component";

//#endregion
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { FormularioAgregarComponent } from './formulario-agregar/formulario-agregar.component';
import { FormularioEditarComponent } from './formulario-editar/formulario-editar.component';


const SucursalesRoutes: Routes = [
  {
    path: "Sucursales",
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
      { path: "agregar", component: FormularioAgregarComponent, data: { vista: 'sucursales' } },
      { path: "editar/:id", component: FormularioEditarComponent, data: { vista: 'sucursales' } },
      { path: "home", component: HomeComponent, data: { vista: 'sucursales' } },
      { path: "", redirectTo: "home", pathMatch: "full" }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(SucursalesRoutes)],
  exports: [RouterModule]
})
export class SucursalesRoutingModule { }
