import { NgModule } from "@angular/core";

//#region rutas
import { Routes, RouterModule } from "@angular/router";
//#endregion rutas

//#region components
import { MainComponent } from "./components/main/main.component";
import { InicioComponent } from "./components/inicio/inicio.component";
import { ModalAgregarComponent } from "./components/modal-agregar/modal-agregar.component";
import { ModalEditarComponent } from "./components/modal-editar/modal-editar.component";
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';
//#endregion components

const ConfiguracionRoutes: Routes = [
  {
    path: "Configuracion",
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
      { path: "editar", component: ModalEditarComponent , data: { vista: 'configuracion' } },
      { path: "crear", component: ModalAgregarComponent, data: { vista: 'configuracion' } },
      { path: "", redirectTo: "home", pathMatch: "full" },
      { path: "home", component: InicioComponent , data: { vista: 'configuracion' } }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(ConfiguracionRoutes)],
  exports: [RouterModule]
})
export class ConfiguracionRoutingModule {}
