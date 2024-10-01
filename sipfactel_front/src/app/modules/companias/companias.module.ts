//#region components
import { HomeComponent } from "./components/home/home.component";
import { MainComponent } from "./components/main/main.component";
import { DatosCompaniaComponent } from "./components/datos-compania/datos-compania.component";
import { TipoOperacionComponent } from "./components/tipo-operacion/tipo-operacion.component";
import { DatosGeneralesComponent } from "./components/datos-generales/datos-generales.component";
//#endregion components

//#region modulos
import { CompaniasRoutingModule } from "./companias.routing";
import { DirectivesModule } from "../directives/directives.module";
import { NgModule } from "@angular/core";
import { ToastrModule } from "ngx-toastr";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
//#endregion modulos

//#region servicios
import { CompaniasService } from "../../services/companias.service";
//#endregion modulos
@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    DatosCompaniaComponent,
    TipoOperacionComponent,
    DatosGeneralesComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ToastrModule.forRoot(),
    CompaniasRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    DirectivesModule
    // MatStepperModule
  ],
  providers: [CompaniasService]
})
export class CompaniasModule {}
