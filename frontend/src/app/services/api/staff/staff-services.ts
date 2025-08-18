import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StaffPost } from '../../../representations/Staff/staffPost';
import { StaffResponse } from '../../../representations/Staff/staffResponce';


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


  createStaff(formData: FormData): Observable<StaffResponse> {
    return this.http.post<StaffResponse>(`${this.baseUrl}/addstaff`, formData);
  }


    updateStaff(id: number, formData: Partial<StaffPost>): Observable<StaffPost> {
      return this.http.put<StaffPost>(`${this.baseUrl}/${id}`, formData);
    }

  
  
}
