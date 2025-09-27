import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import {jwtDecode} from 'jwt-decode';
import { ClubServices } from '../club/club-services';

export interface LoginRequest {
  email: string;
  password: string;
}

interface DecodedToken {
  sub: string;
  role: string;
  clubId?: number;
  exp?: number;
  [key: string]: any;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
    private clubService: ClubServices
  ) {}

  public authenticate(request: LoginRequest): Observable<{ access_token: string }> {
    return this.http.post<{ access_token: string }>(
      `${this.baseUrl}/authenticate`,
      request,
      { withCredentials: true }
    ).pipe(
      tap(response => {
        const token = response.access_token;
        localStorage.setItem('token', token);

        const decoded = this.decodeToken(token);
        localStorage.setItem('decoded', JSON.stringify(decoded));
        if (decoded) {
          // Save role
          if (decoded.role) {
            localStorage.setItem('role', decoded.role);
          }

          // Save clubId if present
          if (decoded.clubId != null) {
            localStorage.setItem('clubId', decoded.clubId.toString());

          } else {
            localStorage.removeItem('clubId');
          }

          console.log('Token saved:', token);
          console.log('Decoded role:', decoded.role);
          console.log('Decoded clubId:', decoded.clubId ?? 'none');
        }
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
    localStorage.removeItem('clubId');
    this.router.navigate(['/login']);
  }

  private decodeToken(token: string): DecodedToken | null {
    try {
      return jwtDecode<DecodedToken>(token);
    } catch (error) {
      console.error('Invalid JWT:', error);
      return null;
    }
  }

}

