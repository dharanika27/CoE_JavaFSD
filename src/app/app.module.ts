import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { ServicesComponent } from './components/services/services.component';
import { TeamsComponent } from './components/teams/teams.component';
import { TeamDetailComponent } from './components/home/team-detail/team-detail.component';
import { LoginComponent } from './login/login.component';
import { ServiceDetailsComponent } from './components/services/service-details/service-details.component';
import { HighlightChampionPipe } from './pipes/highlight-champion.pipe';
import { IplTeamsService } from './services/ipl-teams.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    ContactsComponent,
    ServicesComponent,
    TeamsComponent,
    TeamDetailComponent,
    LoginComponent,
    ServiceDetailsComponent,
    HighlightChampionPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [IplTeamsService],
  bootstrap: [AppComponent]
})
export class AppModule {}
