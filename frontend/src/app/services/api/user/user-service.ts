import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserResponse } from '../../../representations/User/userResponse';
import { UserPost } from '../../../representations/User/userPost';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = "http://localhost:8080";

  constructor(private http: HttpClient) {
    // Constructor implementation
  }

  getUserById(id: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/users/${id}`);
  }

  // Additional methods you might need
  getAllUsers(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(`${this.baseUrl}/users`);
  }

  createUser(user: UserPost): Observable<UserPost> {
    return this.http.post<UserPost>(`${this.baseUrl}/users`, user);
  }

  updateUser(id: number, user: UserPost): Observable<UserPost> {
    return this.http.put<UserPost>(`${this.baseUrl}/users/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/users/${id}`);
  }
}