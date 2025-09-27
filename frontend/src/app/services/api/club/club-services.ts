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

  // Load all clubs
  loadClubs(): Observable<ResponseClub[]> {
    return this.http.get<ResponseClub[]>(this.baseUrl);
  }

  // Get club for current user (JWT contains clubId for club admin)
  getSelectedClub(): Observable<ResponseClub> {
    return this.http.get<ResponseClub>(`${this.baseUrl}/profile`);
  }

  // Create a new club
  createClub(formData: FormData): Observable<ResponseClub> {
    return this.http.post<ResponseClub>(`${this.baseUrl}/register`, formData);
  }

  // Load staff of selected club (backend reads clubId from JWT)
  loadStaff(): Observable<StaffResponse[]> {
    return this.http.get<StaffResponse[]>(`${this.baseUrl}/staff`);
  }

  // Load players of selected club
  loadPlayers(): Observable<PlayerResponce[]> {
    return this.http.get<PlayerResponce[]>(`${this.baseUrl}/players`);
  }

  // Load administration of selected club
  loadAdministration(): Observable<ResponceAdministration[]> {
    return this.http.get<ResponceAdministration[]>(`${this.baseUrl}/administration`);
  }

  // Load categories
  loadCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories`);
  }

  // Add category to selected club
  addCategoryToClub(categoryId: number): Observable<Category> {
    return this.http.post<Category>(`${this.baseUrl}/categories`, { id: categoryId });
  }

  // Remove category from selected club
  removeCategoryFromClub(categoryId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/categories/${categoryId}`);
  }

  // Upload club files
  uploadClubFiles(files: File[], type: string): Observable<any> {
    const formData = new FormData();
    files.forEach(file => formData.append('file', file, file.name));
    formData.append('type', type);

    return this.http.post<any>(`${this.baseUrl}/upload-file`, formData);
  }

  // Load club files
  loadClubFiles(): Observable<ClubFile[]> {
    return this.http.get<ClubFile[]>(`${this.baseUrl}/files`);
  }

  // Load a specific file by ID
  loadClubFileById(fileId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/files/content`, {
      params: { id: fileId.toString() }
    });
  }

  // Delete a club file
  deleteClubFile(fileId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/files`, {
      params: { fileId: fileId.toString() }
    });
  }

  // Update a club file
  updateClubFile(file: File, fileId: number): Observable<ClubFile> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('fileId', fileId.toString());

    return this.http.put<ClubFile>(`${this.baseUrl}/files`, formData);
  }

  // Request member validation
  requestMemberValidation(userId: number, clubId: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/request-validation`, { userId, clubId });
  }

// In club-services.ts
  getClubById(clubId: number): Observable<ResponseClub> {
    return this.http.get<ResponseClub>(`${this.baseUrl}/${clubId}`);
  }

}
