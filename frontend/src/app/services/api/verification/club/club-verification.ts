import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// DTO for club verification request
export interface ClubVerificationRequestDTO {
  id: number;
  clubId: number;
  clubName: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class ClubVerificationRequestService {
  private baseUrl = 'http://localhost:8080/admin/club-verification-requests';

  constructor(private http: HttpClient) {}

  // ✅ Fetch all pending club verification requests
  getPending(): Observable<ClubVerificationRequestDTO[]> {
    return this.http.get<ClubVerificationRequestDTO[]>(`${this.baseUrl}/pending`);
  }

  // ✅ Approve a club verification request
  approve(id: number, adminName: string): Observable<ClubVerificationRequestDTO> {
    const body = { id, adminName };
    return this.http.post<ClubVerificationRequestDTO>(`${this.baseUrl}/approve`, body);
  }

  // ✅ Reject a club verification request
  reject(id: number, adminName: string, reason: string): Observable<ClubVerificationRequestDTO> {
    const body = { id, adminName, reason };
    return this.http.post<ClubVerificationRequestDTO>(`${this.baseUrl}/reject`, body);
  }

  // ✅ Create a new club verification request
  requestVerification(clubId: number): Observable<ClubVerificationRequestDTO> {
    const body = { clubId };
    return this.http.post<ClubVerificationRequestDTO>(`${this.baseUrl}/request`, body);
  }


  
}
