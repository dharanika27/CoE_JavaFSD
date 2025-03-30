import { Component } from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent {
  teams = ['Mumbai Indians', 'Chennai Super Kings', 'Royal Challengers Bangalore', 'Kolkata Knight Riders', 'Delhi Capitals', 'Rajasthan Royals'];

  matches = [
    { date: 'March 25, 2025', team1: this.teams[0], team2: this.teams[1], venue: 'Wankhede Stadium' },
    { date: 'March 26, 2025', team1: this.teams[2], team2: this.teams[3], venue: 'Chinnaswamy Stadium' },
    { date: 'March 27, 2025', team1: this.teams[4], team2: this.teams[5], venue: 'Arun Jaitley Stadium' },
    { date: 'March 28, 2025', team1: this.teams[0], team2: this.teams[2], venue: 'Wankhede Stadium' },
    { date: 'March 29, 2025', team1: this.teams[3], team2: this.teams[5], venue: 'Eden Gardens' },
    { date: 'March 30, 2025', team1: this.teams[1], team2: this.teams[4], venue: 'Chepauk Stadium' }
  ];
}
