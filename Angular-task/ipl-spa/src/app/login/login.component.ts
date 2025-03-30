import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  // Hardcoded credentials
  validUsername = 'admin';
  validPassword = '12345';

  constructor(private router: Router) {}

  login() {
    if (this.username === this.validUsername && this.password === this.validPassword) {
      localStorage.setItem('user', this.username); // Save user session
      alert('Login successful!');
      this.router.navigate(['/']); // Redirect to home page
    } else {
      this.errorMessage = 'Invalid username or password!';
    }
  }
}
