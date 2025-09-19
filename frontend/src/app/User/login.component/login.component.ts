import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService, LoginRequest } from '../../services/api/login/login.service';
import {ClubServices} from '../../services/api/club/club-services';

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
    private clubService :ClubServices,
    private router: Router
  ) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (this.isLoading) return;
    this.isLoading = true;

    this.authService.authenticate(this.loginData).subscribe({
      next: () => {
        // Retrieve role from localStorage (set in AuthService after decoding)
        const role = localStorage.getItem('role');
        if (role === 'ADMIN') {
          this.router.navigate(['/admin/clubs']);
        } else if (role === 'CLUB_ADMIN') {
          this.clubService.selectClub(Number(localStorage.getItem('clubId'))).subscribe({
            next: () => console.log('Club auto-selected:', Number(localStorage.getItem('clubId'))),
            error: (err) => console.error('Failed to auto-select club:', err)
          });
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
