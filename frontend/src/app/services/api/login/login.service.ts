import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

export interface LoginRequest {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient, private router: Router) {}

  public authenticate(request: LoginRequest): Observable<{ access_token: string, role: string }> {
    return this.http.post<{ access_token: string, role: string }>(
      `${this.baseUrl}/authenticate`,
      request
    ).pipe(
      tap(response => {
        localStorage.setItem('token', response.access_token);
        localStorage.setItem('role', response.role);
        console.log('Token saved:', response.access_token);
      })
    );
  }

  public logout(): Observable<any> {
    const token = localStorage.getItem('token');
    return this.http.post(`${this.baseUrl}/logout`, {}, {
      headers: { Authorization: `Bearer ${token}` }
    }).pipe(
      tap(() => this.clearAndRedirect()),
      catchError(err => {
        console.error('Logout failed', err);
        this.clearAndRedirect();
        return of(null);
      })
    );
  }

  private clearAndRedirect(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}
