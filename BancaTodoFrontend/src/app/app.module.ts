import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListaClienteComponent } from './cliente/components/lista-cliente/lista-cliente.component';
import { DetalleClienteComponent } from './cliente/components/detalle-cliente/detalle-cliente.component';
import { NuevoClienteComponent } from './cliente/components/nuevo-cliente/nuevo-cliente.component';
import { EditarClienteComponent } from './cliente/components/editar-cliente/editar-cliente.component';
import { MatSelectModule } from '@angular/material/select';

import{HttpClientModule} from '@angular/common/http';
import{FormsModule} from '@angular/forms';

//externals
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HeaderComponent } from './core/header/header.component';
import { ListAccountsComponent } from './accounts/components/list-accounts/list-accounts.component';
import { ListTransactionsComponent } from './accounts/transactions/components/list-transactions/list-transactions.component';
import { ConsignarComponentsComponent } from './accounts/transactions/components/consignar-components/consignar-components.component';
import { CreateAccountsComponent } from './accounts/components/create-accounts/create-accounts.component';
import { RetirarComponentsComponent } from './accounts/transactions/components/retirar-components/retirar-components.component';
import { TransferirComponentsComponent } from './accounts/transactions/components/transferir-components/transferir-components.component';


@NgModule({
  declarations: [
    AppComponent,
    ListaClienteComponent,
    DetalleClienteComponent,
    NuevoClienteComponent,
    EditarClienteComponent,
    HeaderComponent,
    ListAccountsComponent,
    ListTransactionsComponent,
    ConsignarComponentsComponent,
    CreateAccountsComponent,
    RetirarComponentsComponent,
    TransferirComponentsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    FormsModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
