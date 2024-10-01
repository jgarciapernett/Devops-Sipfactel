import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RolesModule } from './modules/roles/roles.module';

import * as bootstrap from "bootstrap";
import * as $ from "jquery";

import {
  HashLocationStrategy,
  LocationStrategy,
  DecimalPipe,
  DatePipe
} from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

//#region routing
import { routing, appRoutingProviders } from '../app/app.routing';
//#endregion routing

//#region agregar componentes
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MenuGrupoComponent } from './components/sidebar/menu-grupo/menu-grupo.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
//#endregion agregar componentes

//#region importar modulos
import { SucursalesModule } from './modules/sucursales/sucursales.module';
import {ErroresModule} from './modules/errores/errores.module'
import { ResolucionFacturasModule } from './modules/resolucionFacturas/resolucionFacturas.module';
import { NotasCreditoDebitoModule } from './modules/notasCreditoDebito/notasCreditoDebito.module';

import {AdquirientesModule} from './modules/adquiriente/adquiriente.module';
import { LoginJwtModule } from './modules/login-jwt/login-jwt.module';

import { TablasModule } from './modules/tablas/tablas.module';
import { UsuariosModule } from './modules/usuarios/usuarios.module';
import { ReportesModule } from './modules/reportes/reportes.module';
import { InformeModule } from './modules/reportes/informe.module';
import { ReporteAdquirientesModule } from './modules/reportes/reporteAdquirientes.module';
//#endregion importar modulos

//#region modulos externos
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { NgIdleKeepaliveModule } from '@ng-idle/keepalive';
import { MomentModule } from 'angular2-moment';
import { ModalModule, BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ParametrosModule } from './modules/parametros/parametros.module';
import { SistemaModule } from './modules/sistema/sistema.module';
import { HorasEtlModule } from './modules/horas-etl/horas-etl.module';
import {NgxPaginationModule} from 'ngx-pagination';

//#endregion modulos externos

//#region Guards
import { AuthGuard } from './guards/auth.guard';
import { LoginGuard } from './guards/login.guard';
//#endregion Guards

//#region Servicios
import { UtilidadesService } from './services/utilidades.service';
// interceptors
import { httpInterceptorProviders } from './interceptors';
import { CompaniasModule } from './modules/companias/companias.module';
import {HomologacionModule} from './modules/homologacion/homologacion.module';
import { ConfiguracionModule } from './modules/configuracion/configuracion.module';
import { AuditoriaModule } from './modules/auditoria/auditoria.module';
import { PolizasModule } from './modules/polizas/polizas.module';
import { ValoresDefectoModule } from './modules/valores-defecto/valores-defecto.module';
import { EtlComponent } from './components/etl/etl.component';
import { EtlService } from './services/etl.service';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    MenuGrupoComponent,
    SidebarComponent,
    EtlComponent
  ],
  imports: [
    BrowserModule,
    UsuariosModule,
    NgxPaginationModule,
    SistemaModule,
    HorasEtlModule,
    BrowserModule,
    routing,
    ParametrosModule,
    HttpClientModule,
    NgbModule,
    AuditoriaModule,
    FormsModule,
    RolesModule,
    CompaniasModule,
    LoginJwtModule,
    // EjemplosModule,
    BrowserAnimationsModule,
    AdquirientesModule,
    ToastrModule.forRoot(), // ToastrModule
    ToastrModule.forRoot(),
    NgxSpinnerModule,
    Ng4LoadingSpinnerModule.forRoot(),
    TablasModule,
    ConfiguracionModule,
    HomologacionModule,
    ErroresModule,
    ParametrosModule,
    PolizasModule,
    ReportesModule,
    InformeModule,
    ReporteAdquirientesModule,
    SucursalesModule,
    ResolucionFacturasModule,
    NotasCreditoDebitoModule,
    NgxSpinnerModule,
    ValoresDefectoModule,
    ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
    NgIdleKeepaliveModule.forRoot(),
    MomentModule,
    ModalModule.forRoot(),
  ],
  providers: [
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    appRoutingProviders,
    LoginGuard,
    AuthGuard,
    UtilidadesService,
    DecimalPipe,
    DatePipe,
    httpInterceptorProviders,
    EtlService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
