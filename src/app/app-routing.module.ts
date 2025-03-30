import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { ServicesComponent } from './components/services/services.component';
import { TeamsComponent } from './components/teams/teams.component';
import { TeamDetailComponent } from './components/home/team-detail/team-detail.component';
import { LoginComponent } from './login/login.component';
import { ServiceDetailsComponent } from './components/services/service-details/service-details.component';
import { AuthGuard } from './guards/auth.guard'; // Import the AuthGuard

const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'about', component: AboutComponent },
  { path: 'contacts', component: ContactsComponent, canActivate: [AuthGuard] }, // Protected
  { path: 'teams', component: TeamsComponent, canActivate: [AuthGuard] }, 
  { path: 'teams/:name', component: TeamDetailComponent, canActivate: [AuthGuard] }, 
  { path: 'services', component: ServicesComponent, canActivate: [AuthGuard] },
  { path: 'services/:name', component: ServiceDetailsComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
