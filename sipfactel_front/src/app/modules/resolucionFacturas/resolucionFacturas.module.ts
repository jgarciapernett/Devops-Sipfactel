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
import { ResolucionFacturasRoutingModule } from "./resolucionFacturas.routing";
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
import { ModalCreaEditaResolucionFacturaComponent } from './modal-crea-edita-resolucion-factura/modal-crea-edita-resolucion-factura.component';
//#endregion
@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    ModalCreaEditaResolucionFacturaComponent
  ],
  imports: [
    CommonModule,
    NgxPaginationModule,
    ResolucionFacturasRoutingModule,
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
  providers: [Ng4LoadingSpinnerService, SucursalService, PaginacionService],
  entryComponents: [
		ModalCreaEditaResolucionFacturaComponent
	]
})
export class   ResolucionFacturasModule {}
