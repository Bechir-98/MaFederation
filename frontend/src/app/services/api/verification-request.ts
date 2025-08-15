import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface VerificationRequestDTO {
  id: number;
  userId: number;
  userName: string;
  clubId: number;
  clubName: string;
  createdAt :string;
}

@Injectable({ providedIn: 'root' })
export class VerificationRequestService {
  private baseUrl = 'http://localhost:8080/admin/verification-requests';

  constructor(private http: HttpClient) {}

  // ✅ Fetch all pending verification requests
  getPending(): Observable<VerificationRequestDTO[]> {
    return this.http.get<VerificationRequestDTO[]>(`${this.baseUrl}/pending`);
  }

approve(id: number, adminName: string): Observable<VerificationRequestDTO> {
  const body = { id, adminName };
  return this.http.post<VerificationRequestDTO>(
    `${this.baseUrl}/approve`,
    body
  );
}

// ✅ Reject a request (id, adminName, reason in body)
reject(id: number, adminName: string, reason: string): Observable<VerificationRequestDTO> {
  const body = { id, adminName, reason };
  return this.http.post<VerificationRequestDTO>(
    `${this.baseUrl}/reject`,
    body
  );
}



 


}
