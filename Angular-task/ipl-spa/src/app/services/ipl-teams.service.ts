import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface Team {
  id: number;
  name: string;
  logo: string;
  description: string;
  captain: string;
  coach: string;
  homeGround: string;
  titles: number;
}

@Injectable({
  providedIn: 'root'
})
export class IplTeamsService {
  private jsonUrl = 'assets/ipl-teams.json'; // Local JSON for testing
  private apiUrl = 'https://api.example.com/ipl-teams'; // Replace with actual API

  constructor(private http: HttpClient) {}

  /**
   * Fetch IPL Teams from local JSON or real API
   */
  getTeams(useLocal: boolean = true): Observable<Team[]> {
    const url = useLocal ? this.jsonUrl : this.apiUrl;
    return this.http.get<Team[]>(url).pipe(
      catchError(error => {
        console.error('Error fetching IPL teams:', error);
        return throwError(() => new Error('Failed to fetch IPL teams. Try again later.'));
      })
    );
  }
}
