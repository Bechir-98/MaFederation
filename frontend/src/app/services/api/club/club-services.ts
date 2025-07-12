import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClubRepresentation } from '../../../representations/club-representation';

@Injectable({
  providedIn: 'root'
})
export class ClubServices {
  private readonly apiUrl = 'http://localhost:8080/api/clubs'; // Replace with your actual backend endpoint

  constructor(private http: HttpClient) {}

  loadClubs(): Observable<ClubRepresentation[]> {
    return this.http.get<ClubRepresentation[]>(this.apiUrl);
  }
}
