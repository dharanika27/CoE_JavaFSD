import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {
  teams: any[] = []; // Store JSON data dynamically

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadTeams();
  }

  // Fetch JSON data from assets folder
  loadTeams() {
    this.http.get<any[]>('assets/ipl-teams.json').subscribe({
      next: (data) => {
        this.teams = data;
        console.log('Teams loaded successfully:', this.teams);
      },
      error: (err) => {
        console.error('Error loading team data:', err);
      }
    });
  }
}
