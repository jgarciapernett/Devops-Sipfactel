//#region components
import { MainComponent } from "./components/main/main.component";
import { HomeComponent } from "./components/home/home.component";
import { DatosCompaniaComponent } from "./components/datos-compania/datos-compania.component";
import { DatosGeneralesComponent } from "./components/datos-generales/datos-generales.component";
import { TipoOperacionComponent } from "./components/tipo-operacion/tipo-operacion.component";
//#endregion components

//#region modulos
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
//#endregion modulos

import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';

const CompaniasRoutes: Routes = [
  {
    path: "Companias",
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
      { path: "datos-compania", component: DatosCompaniaComponent, data: { vista: 'companias' } },
      { path: "datos-generales", component: DatosGeneralesComponent, data: { vista: 'companias' } },
      { path: "tipo-operacion", component: TipoOperacionComponent, data: { vista: 'companias' } },
      { path: "home", component: HomeComponent, data: { vista: 'companias' } },
      { path: "**", redirectTo: "home", pathMatch: "full" }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(CompaniasRoutes)],
  exports: [RouterModule]
})
export class CompaniasRoutingModule { }
