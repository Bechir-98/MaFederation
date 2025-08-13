import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StaffRepresentation } from '../../../representations/Staff/staffResponce';


@Injectable({
  providedIn: 'root'
})
export class StaffServices {
  private baseUrl = 'http://localhost:8080/staff';

  constructor(private http: HttpClient) {}

  /**
   * Create a new staff member
   * @param formData multipart/form-data containing JSON and optional profile picture
   */


  // Select a staff member in the session
selectStaff(staffId: number): Observable<void> {
  return this.http.post<void>(`${this.baseUrl}`, { staffId }, { withCredentials: true });
}

// Get the selected staff profile
getSelectedStaff(): Observable<StaffRepresentation> {
  return this.http.get<StaffRepresentation>(`${this.baseUrl}/profile`, { withCredentials: true });
}


  createStaff(formData: FormData): Observable<StaffRepresentation> {
    return this.http.post<StaffRepresentation>(`${this.baseUrl}/addstaff`, formData);
  }
















  updateStaff(id: number, formData: FormData): Observable<StaffRepresentation> {
    return this.http.put<StaffRepresentation>(`${this.baseUrl}/${id}`, formData);
  }

  /**
   * Delete staff member
   */
  deleteStaff(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
