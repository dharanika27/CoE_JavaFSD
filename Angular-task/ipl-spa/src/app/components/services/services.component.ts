import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent {
  servicesList = [
    { id: 'player-statistics', name: 'Player Statistics', description: 'Get insights into player performance.' },
    { id: 'team-analysis', name: 'Team Analysis', description: 'Compare team stats and strengths.' },
    { id: 'live-match-scores', name: 'Live Match Scores', description: 'Stay updated with real-time scores.' }
  ];

  constructor(private router: Router) {
    console.log('Services Component Loaded');
    console.log('Services List:', this.servicesList);
  }

  navigateToDetails(serviceName: string) {
    let formattedName = serviceName.toLowerCase().replace(/\s+/g, '-'); // Convert to URL-friendly format
    this.router.navigate(['/services', formattedName]);
  }
}