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


const NOTAS_ROUTES: Routes = [
  {
    path: "notas", component: MainComponent,
    canActivate: [
      LoginGuard,
      AuthGuard
    ],
    canActivateChild: [
      LoginGuard,
      AuthGuard
    ],
    children: [
      { path: "home", component: HomeComponent, data: { vista: 'nota' } },
      { path: "", redirectTo: "home", pathMatch: "full" }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(NOTAS_ROUTES)],
  exports: [RouterModule]
})
export class NotasRoutingModule { }
