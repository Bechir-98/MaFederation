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
      next: (response) => {
        console.log('Login success', response);

        // Save the correct properties
        localStorage.setItem('token', response.access_token); // <-- changed here
        localStorage.setItem('role', response.role);

        // Redirect user based on role
        if (response.role === 'ADMIN') {
          this.router.navigate(['/admin/clubs']);
        } else if (response.role === 'USER') {
          this.router.navigate(['/user/home']);
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
