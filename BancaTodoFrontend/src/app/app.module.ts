import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListaClienteComponent } from './cliente/components/lista-cliente/lista-cliente.component';
import { DetalleClienteComponent } from './cliente/components/detalle-cliente/detalle-cliente.component';
import { NuevoClienteComponent } from './cliente/components/nuevo-cliente/nuevo-cliente.component';
import { EditarClienteComponent } from './cliente/components/editar-cliente/editar-cliente.component';

import{HttpClientModule} from '@angular/common/http';
import{FormsModule} from '@angular/forms';

//externals
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HeaderComponent } from './core/header/header.component';
import { ListAccountsComponent } from './accounts/components/list-accounts/list-accounts.component';
import { ListTransactionsComponent } from './accounts/transactions/components/list-transactions/list-transactions.component';
import { ConsignarComponentsComponent } from './accounts/transactions/components/consignar-components/consignar-components.component';


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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
