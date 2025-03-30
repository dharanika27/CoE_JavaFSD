import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import teams from '../../../../assets/teams.json';

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['./team-detail.component.css']
})
export class TeamDetailComponent implements OnInit {
  team: any | undefined;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    const teamName=this.route.snapshot.params['name'];
    this.team = teams.teams.find(team => team.name === teamName);
    console.log(this.team);
  }
}