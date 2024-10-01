import { NgModule } from "@angular/core";
import { ToastrModule } from "ngx-toastr";
import { CommonModule } from "@angular/common";

// modulos externos
import { Ng4LoadingSpinnerService } from "ng4-loading-spinner";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

//#region rutas
import { HomologacionRoutingModule } from "./homologacion.routing";
import { RouterModule } from "@angular/router";
//#endregion rutas

// modulos internos

import { TablasModule } from "../tablas/tablas.module";
import { DirectivesModule } from "../directives/directives.module";
import { HttpClientModule } from "@angular/common/http";

// componentes
import { InicioComponent } from "./components/inicio/inicio.component";
import { MainComponent } from "./components/main/main.component";
import { ModalAgregarComponent } from "./components/modal-agregar/modal-agregar.component";
import { ModalEditarComponent } from "./components/modal-editar/modal-editar.component";

// servicios

import { homologacionService } from "../../services/homologacion.service";

@NgModule({
  declarations: [
    MainComponent,
    InicioComponent,
    ModalAgregarComponent,
    ModalEditarComponent,
  ],

  imports: [
    CommonModule,
    HomologacionRoutingModule,
    ToastrModule.forRoot(),
    FormsModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    TablasModule,
    BrowserAnimationsModule,
    DirectivesModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})

  ],
  providers: [homologacionService, Ng4LoadingSpinnerService]
})
export class HomologacionModule {}
