import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponceAdministration } from '../../../representations/Admin/ResponceAdministration';
import { PostAdministration } from '../../../representations/Admin/PostAdministration';

@Injectable({
  providedIn: 'root'
})
export class AdminstrationService {
  private baseUrl = 'http://localhost:8080/administration'; // Adjust base URL as needed

  constructor(private http: HttpClient) {}

  createAdministration(formData: FormData): Observable<ResponceAdministration> {
    return this.http.post<ResponceAdministration>(`${this.baseUrl}/add`, formData, { withCredentials: true });
  }

 updateAdmin(id: number, payload: Partial<ResponceAdministration>): Observable<ResponceAdministration> {
  return this.http.put<ResponceAdministration>(`${this.baseUrl}/${id}`, payload);
}



}
