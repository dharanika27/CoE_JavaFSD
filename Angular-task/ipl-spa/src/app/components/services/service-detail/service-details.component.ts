import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { IplTeamsService, Team } from '../../../services/ipl-teams.service';

@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrls: ['./service-details.component.css']
})
export class ServiceDetailsComponent implements OnInit {
  serviceId: string | null = null;
  serviceDetails: any = null;
  liveMatchData: any = null;
  teams: Team[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private iplTeamsService: IplTeamsService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.serviceId = params.get('name');
      this.loadServiceDetails();

      switch (this.serviceId) {
        case 'live-match-scores':
          this.fetchLiveMatches();
          break;
        case 'team-analysis':
          this.fetchTeams();
          break;
      }
    });
  }

  loadServiceDetails(): void {
    const servicesData: Record<string, any> = {
      'player-statistics': {
        title: 'Player Statistics',
        description: 'Detailed stats of IPL players.',
        details: [
          { name: 'Virat Kohli', stat: 'Runs', avg: '55.3' },
          { name: 'MS Dhoni', stat: 'Catches', avg: '30' },
          { name: 'Rohit Sharma', stat: 'Runs', avg: '48.7' },
          { name: 'Jasprit Bumrah', stat: 'Wickets', avg: '15.2' },
          { name: 'KL Rahul', stat: 'Strike Rate', avg: '135.4' }
        ]
      }
    };

    this.serviceDetails = servicesData[this.serviceId as keyof typeof servicesData] || null;
  }

  fetchLiveMatches(): void {
    const apiUrl = 'https://api.cricapi.com/v1/currentMatches?apikey=YOUR_API_KEY';
    
    this.http.get(apiUrl).pipe(
      catchError(error => {
        console.error('Error fetching live match data:', error);
        return throwError(() => new Error('Failed to load live match data.'));
      })
    ).subscribe(
      (response: any) => {
        if (response && response.data && response.data.length) {
          this.liveMatchData = response.data;
          this.serviceDetails = {
            title: 'Live Match Scores',
            description: 'Real-time updates on IPL matches.',
            details: response.data.map((match: any) => ({
              match: match.name,
              score: match.score || 'N/A'
            }))
          };
        } else {
          console.warn('No live match data available.');
          this.liveMatchData = null;
        }
      }
    );
  }

  fetchTeams(): void {
    this.iplTeamsService.getTeams().pipe(
      catchError(error => {
        console.error('Error fetching team details:', error);
        return throwError(() => new Error('Failed to load IPL teams.'));
      })
    ).subscribe(
      (data: Team[]) => {
        this.teams = data;
        this.serviceDetails = {
          title: 'Team Analysis',
          description: 'Performance analysis of IPL teams.',
          details: this.teams.map(team => ({
            name: team.name,
            stat: `Titles: ${team.titles}`,
            strength: team.description
          }))
        };
      }
    );
  }

  goBack(): void {
    this.router.navigate(['/services']);
  }
}
