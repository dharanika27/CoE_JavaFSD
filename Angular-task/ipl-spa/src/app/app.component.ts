import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private router: Router) {}

  // Check if user is logged in based on localStorage
  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }

  // Logout function
  logout() {
    localStorage.removeItem('user'); // Remove user from storage
    this.router.navigate(['/login']); // Redirect to login page
  }
}
