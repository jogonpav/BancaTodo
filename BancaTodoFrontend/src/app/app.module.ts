import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListaClienteComponent } from './cliente/components/lista-cliente/lista-cliente.component';
import { DetalleClienteComponent } from './cliente/components/detalle-cliente/detalle-cliente.component';
import { NuevoClienteComponent } from './cliente/components/nuevo-cliente/nuevo-cliente.component';
import { EditarClienteComponent } from './cliente/components/editar-cliente/editar-cliente.component';
import { MatSelectModule } from '@angular/material/select';

import{HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import{FormsModule, ReactiveFormsModule} from '@angular/forms';

//externals
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HomeComponent } from './core/header/home.component';
import { ListAccountsComponent } from './accounts/components/list-accounts/list-accounts.component';
import { ListTransactionsComponent } from './accounts/transactions/components/list-transactions/list-transactions.component';
import { ConsignarComponentsComponent } from './accounts/transactions/components/consignar-components/consignar-components.component';
import { CreateAccountsComponent } from './accounts/components/create-accounts/create-accounts.component';
import { RetirarComponentsComponent } from './accounts/transactions/components/retirar-components/retirar-components.component';
import { TransferirComponentsComponent } from './accounts/transactions/components/transferir-components/transferir-components.component';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatInputModule } from '@angular/material/input';
import { LoginComponent } from './core/login/login.component';
import { HttpConfigInterceptor } from './core/HttpConfigInterceptor';
import { CreateUserComponent } from './user/components/create-user/create-user.component';
import { EditUserComponent } from './user/components/edit-user/edit-user.component';
import { ResetPasswordComponent } from './user/components/reset-password/reset-password.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';




@NgModule({
  declarations: [
    AppComponent,
    ListaClienteComponent,
    DetalleClienteComponent,
    NuevoClienteComponent,
    EditarClienteComponent,
    HomeComponent,
    ListAccountsComponent,
    ListTransactionsComponent,
    ConsignarComponentsComponent,
    CreateAccountsComponent,
    RetirarComponentsComponent,
    TransferirComponentsComponent,
    LoginComponent,
    CreateUserComponent,
    EditUserComponent,
    ResetPasswordComponent,



  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    //user
    FormsModule,
    MatSelectModule,
    MatCardModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDividerModule,
    MatButtonModule,
    MatDialogModule,
    MatSnackBarModule,
    SweetAlert2Module.forRoot()
  ],
  providers: [{provide: HTTP_INTERCEPTORS,useClass: HttpConfigInterceptor, multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
