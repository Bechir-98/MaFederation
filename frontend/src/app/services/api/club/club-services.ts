

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClubFile, ResponseClub } from '../../../representations/Club/ResponseClub';

import { PlayerResponce } from '../../../representations/Player/playerResponce';
import { ResponceAdministration } from '../../../representations/Admin/ResponceAdministration';
import { Category } from '../../../representations/Category/category';
import { StaffResponse } from '../../../representations/Staff/staffResponce';

@Injectable({
  providedIn: 'root'
})
export class ClubServices {
  private baseUrl: string = 'http://localhost:8080/clubs';

  constructor(private http: HttpClient) {}

  // Load all clubs (no session needed)
  loadClubs(): Observable<ResponseClub[]> {
    return this.http.get<ResponseClub[]>(this.baseUrl);
  }

  // Select a club by ID, store in session
  selectClub(clubId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/select`, { clubId }, { withCredentials: true });
  }

  // Get the selected club profile (from session)
  getSelectedClub(): Observable<ResponseClub> {
    return this.http.get<ResponseClub>(`${this.baseUrl}/profile`, { withCredentials: true });
  }

  // Create a new club (no session needed)
  createClub(formData: FormData): Observable<ResponseClub> {
    return this.http.post<ResponseClub>(`${this.baseUrl}/register`, formData);
  }

  // Load staff of selected club (clubId is from session on backend)
  loadStaff(): Observable<StaffResponse[]> {
    return this.http.get<StaffResponse[]>(`${this.baseUrl}/staff`,{ withCredentials: true });
  }

  // Load players of selected club
  loadPlayers(): Observable<PlayerResponce[]> {
    return this.http.get<PlayerResponce[]>(`${this.baseUrl}/players`,{ withCredentials: true } );
  }

  // Load administration of selected club
  loadAdministration(): Observable<ResponceAdministration[]> {
    return this.http.get<ResponceAdministration[]>(`${this.baseUrl}/administration`,{ withCredentials: true });
  }


  
// Load categories of selected club
  loadCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories`,{ withCredentials: true });
  }

  // Add category to selected club
  addCategoryToClub(categoryId: number): Observable<Category> {
    return this.http.post<Category>(`${this.baseUrl}/categories`, { id: categoryId },{ withCredentials: true });
  }

  // Remove category from selected club
  removeCategoryFromClub(categoryId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/categories/${categoryId}`,{ withCredentials: true });
  }







  // In your club-services.ts or wherever the service is

uploadClubFiles(files: File[], type: string): Observable<any> {
    const formData = new FormData();
    files.forEach(file => formData.append('file', file, file.name));
    formData.append('type', type);

    return this.http.post<any>(`${this.baseUrl}/upload-file`, formData, {
      withCredentials: true
    });
  }

  loadClubFiles(): Observable<ClubFile[]> {
    return this.http.get<ClubFile[]>(`${this.baseUrl}/files`, {
      withCredentials: true
    });
  }

  loadClubFileById(fileId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/files/content`, {
      params: { id: fileId.toString() },
      withCredentials: true
    });
  }

  deleteClubFile(fileId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/files`, {
      params: { fileId: fileId.toString() },
      withCredentials: true
    });
  }

  updateClubFile(file: File, fileId: number): Observable<ClubFile> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('fileId', fileId.toString());

    return this.http.put<ClubFile>(`${this.baseUrl}/files`, formData, {
      withCredentials: true
    });
  }

   requestMemberValidation(userId: number, clubId: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/request-validation`, {
      userId,
      clubId
    }, { withCredentials: true });
  }

}












