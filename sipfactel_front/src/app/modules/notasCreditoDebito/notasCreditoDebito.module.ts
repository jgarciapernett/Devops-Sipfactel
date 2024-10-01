import { NgModule } from "@angular/core";
import { ToastrModule } from "ngx-toastr";
import { CommonModule } from "@angular/common";

//#region modulos externos
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {NgxPaginationModule} from 'ngx-pagination';
//#endregion

//#region rutas
import { NotasRoutingModule } from "./notasCreditoDebito.routing";
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

// modales
import { ModalModule } from 'ngx-bootstrap/modal';

//#endregion

//#region servicios
import { SucursalService } from "../../services/sucursal.service";
import { PaginacionService } from "../../services/paginacion.service";
import { TablaResolucionesComponent } from './tabla-resoluciones/tabla-resoluciones.component';
import { ModalCreaEditaComponent } from './modal-crea-edita/modal-crea-edita.component';
//#endregion
@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    TablaResolucionesComponent,
    ModalCreaEditaComponent
  ],
  imports: [
    CommonModule,
    NotasRoutingModule,
    NgxPaginationModule,
    ToastrModule.forRoot(),
    ModalModule.forRoot(),
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
  providers: [Ng4LoadingSpinnerService, SucursalService, PaginacionService],
  entryComponents: [
		ModalCreaEditaComponent
	]
})
export class   NotasCreditoDebitoModule {}
