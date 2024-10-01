import { NgModule } from "@angular/core";
import { ToastrModule } from "ngx-toastr";
import { CommonModule } from "@angular/common";

//#region modulos externos
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
//#endregion

//#region rutas
import { SucursalesRoutingModule } from "./sucursales.routing";
import { RouterModule } from "@angular/router";
//#endregion rutas

//#region modules internos
import { TablasModule } from "../tablas/tablas.module";
import { DirectivesModule } from "../directives/directives.module";
import { HttpClientModule } from "@angular/common/http";
//#endregion

//#region componentes
import { MainComponent } from "./main/main.component";
import { HomeComponent } from "./home/home.component";

//#endregion

//#region servicios
import { SucursalService } from "../../services/sucursal.service";
import { PaginacionService } from "../../services/paginacion.service";
import { FormularioAgregarComponent } from './formulario-agregar/formulario-agregar.component';
import { FormularioEditarComponent } from './formulario-editar/formulario-editar.component';
//#endregion
@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    FormularioAgregarComponent,
    FormularioEditarComponent
  ],
  imports: [
    CommonModule,
    SucursalesRoutingModule,
    ToastrModule.forRoot(),
    FormsModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    TablasModule,
    BrowserAnimationsModule,
    DirectivesModule,
    ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: "never" })
  ],
  providers: [Ng4LoadingSpinnerService, SucursalService, PaginacionService]
})
export class   SucursalesModule {}
