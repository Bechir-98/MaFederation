import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserRepresentation } from '../../../representations/User/user-representation';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = "http://localhost:8080";

  constructor(private http: HttpClient) {
    // Constructor implementation
  }

  getUserById(id: number): Observable<UserRepresentation> {
    return this.http.get<UserRepresentation>(`${this.baseUrl}/users/${id}`);
  }

  // Additional methods you might need
  getAllUsers(): Observable<UserRepresentation[]> {
    return this.http.get<UserRepresentation[]>(`${this.baseUrl}/users`);
  }

  createUser(user: UserRepresentation): Observable<UserRepresentation> {
    return this.http.post<UserRepresentation>(`${this.baseUrl}/users`, user);
  }

  updateUser(id: number, user: UserRepresentation): Observable<UserRepresentation> {
    return this.http.put<UserRepresentation>(`${this.baseUrl}/users/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/users/${id}`);
  }
}