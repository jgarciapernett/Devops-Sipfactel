import { NgModule} from '@angular/core';

// rutas
import { Routes, RouterModule } from '@angular/router';


//#region components
import { MainComponent } from './main/main.component' ;
import { HomeComponent } from './home/home.component';
import { DatosGeneralesComponent } from './datos-generales/datos-generales.component';
import { DatosProveedorComponent } from './datos-proveedor/datos-proveedor.component';
import { LoginGuard } from 'src/app/guards/login.guard';
import { AuthGuard } from 'src/app/guards/auth.guard';


//#endregion components

const ParametrosRoutes: Routes = [
  {
    path: 'Parametros',
    component: MainComponent,
    canActivate: [
      LoginGuard,
      AuthGuard
    ],
    canActivateChild:[
      LoginGuard,
      AuthGuard
    ],
    children: [
      
      { path: 'datos-generales', component: DatosGeneralesComponent,data: { vista:'parametros'} },
      { path: 'datos-proveedor', component: DatosProveedorComponent ,data: { vista:'parametros'}},
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent , data: { vista:'parametros'}}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(ParametrosRoutes)],
  exports: [RouterModule]
})
export class ParametrosRoutingModule {}