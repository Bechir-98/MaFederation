import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClubRepresentation } from '../../../representations/Club/club-representation';

@Injectable({
  providedIn: 'root'
})
export class ClubServices {
  private baseUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}


  loadClubs(): Observable<ClubRepresentation[]> {
    return this.http.get<ClubRepresentation[]>(this.baseUrl + '/clubs');
  }

    // ✅ Add this method
  getClubById(id: number): Observable<ClubRepresentation> {
    return this.http.get<ClubRepresentation>(`${this.baseUrl}/clubs/${id}`);
  }

  // ✅ Create a new club
  createClub(club: ClubRepresentation): Observable<ClubRepresentation> {
    return this.http.post<ClubRepresentation>(`${this.baseUrl}`, club);
  }

  uploadMember(memberData: FormData): Observable<any> {
    return this.http.post(this.baseUrl + '/addmember', memberData);
  }
}
