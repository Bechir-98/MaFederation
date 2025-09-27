import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService, LoginRequest } from '../../services/api/login/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData: LoginRequest = {
    email: '',
    password: ''
  };

  showPassword = false;
  rememberMe = false;
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (this.isLoading) return;
    this.isLoading = true;

    this.authService.authenticate(this.loginData).subscribe({
      next: (tokenResponse) => {
        // Decode role and optional clubId from token
        const role = localStorage.getItem('role'); // AuthService stores role
        const clubId = Number(localStorage.getItem('clubId')); // AuthService stores clubId if CLUB_ADMIN

        if (role === 'ADMIN') {
          this.router.navigate(['/admin/clubs']);
        } else if (role === 'CLUB_ADMIN') {
          if (!clubId) {
            console.warn('No clubId in JWT for CLUB_ADMIN');
          }
          // No need to call selectClub() since backend reads clubId from JWT
          this.router.navigate(['/club']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        console.error('Login failed', err);
        this.isLoading = false;
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  onForgotPassword(): void {
    this.router.navigate(['/forgot-password']);
  }
}
