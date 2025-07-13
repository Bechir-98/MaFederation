import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClubRepresentation } from '../../../representations/club-representation';

@Injectable({
  providedIn: 'root'
})
export class ClubServices {
  private baseUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Get all clubs
  loadClubs(): Observable<ClubRepresentation[]> {
    return this.http.get<ClubRepresentation[]>(this.baseUrl+"/clubs");
  }

  // Get club by ID
  getClubById(clubId: number): Observable<ClubRepresentation> {
    return this.http.get<ClubRepresentation>(`${this.baseUrl}/${clubId}`);
  }

  // Create new club
  createClub(club: ClubRepresentation): Observable<ClubRepresentation> {
    return this.http.post<ClubRepresentation>(this.baseUrl, club);
  }

  // Update existing club
  updateClub(clubId: number, club: ClubRepresentation): Observable<ClubRepresentation> {
    return this.http.put<ClubRepresentation>(`${this.baseUrl}/${clubId}`, club);
  }

  // Delete club
  deleteClub(clubId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${clubId}`);
  }

  // Validate club by federation
  validateClub(clubId: number, userId: number): Observable<ClubRepresentation> {
    return this.http.put<ClubRepresentation>(`${this.baseUrl}/${clubId}/validate`, { validatedByUserId: userId });
  }

  // Reject club validation
  rejectClub(clubId: number, rejectionReason: string): Observable<ClubRepresentation> {
    return this.http.put<ClubRepresentation>(`${this.baseUrl}/${clubId}/reject`, { rejectionReason });
  }

  // Get validated clubs only
  getValidatedClubs(): Observable<ClubRepresentation[]> {
    return this.http.get<ClubRepresentation[]>(`${this.baseUrl}/validated`);
  }

  // Get pending clubs only
  getPendingClubs(): Observable<ClubRepresentation[]> {
    return this.http.get<ClubRepresentation[]>(`${this.baseUrl}/pending`);
  }
}