import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginJwtService } from '../../services/login-jwt.service';
import { loginJwtRoutingModule } from './login-jwt.routing';

import { MainComponent } from './components/main/main.component';
import { InComponent } from './components/in/in.component';
import { OutComponent } from './components/out/out.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [InComponent, OutComponent, MainComponent],
  imports: [
    
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    loginJwtRoutingModule
  ],
  providers:[LoginJwtService]
})
export class LoginJwtModule { }
