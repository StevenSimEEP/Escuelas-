import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EscuelasComponent } from './escuelas/escuelas.component';
import { EscuelaDetailComponent } from './escuela-detail/escuela-detail.component';
import { MensajesComponent } from './mensajes/mensajes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EscuelaSearchComponent } from './escuela-search/escuela-search.component';

@NgModule({
  declarations: [
    AppComponent,
    EscuelasComponent,
    EscuelaDetailComponent,
    MensajesComponent,
    DashboardComponent,
    EscuelaSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
