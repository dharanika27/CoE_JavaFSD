import { Component, OnInit } from '@angular/core';
import { IplTeamsService } from '../../services/ipl-teams.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  iplTeams: any[] = [];
  successMessage: string = '';
  teams:string[]=['Mumbai Indians', 'Royal Challengers Bangalore', 'Kolkata Knight Riders', 'Chennai Super Kings', 'Delhi Capitals', 'Sunrisers Hyderabad']

  teamForm = new FormGroup({
    teamName: new FormControl('', [Validators.required, Validators.minLength(3)]),
    captain: new FormControl('', Validators.required),
    coach: new FormControl('', Validators.required)
  });

  constructor(private iplService: IplTeamsService, private router:Router) {}

  ngOnInit(): void {
    this.fetchTeams();
  }

  fetchTeams() {
    this.iplService.getTeams(true).subscribe({
      next: (data) => this.iplTeams = data,
      error: (err) => console.error('Error fetching IPL teams:', err)
    });
  }

  teamDetails(team:any){
    console.log(team.name)
    this.router.navigate(['/teams',team.name ])

  }

  onSubmit() {
    if (this.teamForm.valid) {
      const teamName = this.teamForm.value.teamName?.trim();
      if (!teamName) return;

      const newTeam = {
        id: teamName.toLowerCase().replace(/\s+/g, '-'),
        name: teamName,
        captain: this.teamForm.value.captain || 'Unknown',
        coach: this.teamForm.value.coach || 'Unknown',
        titles: 0,
        logo: 'assets/images/default-team.png',
        description: 'New IPL team'
      };

      this.iplTeams = [...this.iplTeams, newTeam];
      this.successMessage = `✅ Team "${newTeam.name}" registered successfully!`;
      this.teamForm.reset();
    }
  }
}