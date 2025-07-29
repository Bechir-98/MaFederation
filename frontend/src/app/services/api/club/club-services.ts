import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostClub } from '../../../representations/Club/PostClub';
import {  ResponseClub } from '../../../representations/Club/ResponseClub';


@Injectable({
  providedIn: 'root'
})
export class ClubServices {
  private baseUrl: string = 'http://localhost:8080/clubs';

  constructor(private http: HttpClient) {}


  loadClubs(): Observable<ResponseClub[]> {
    return this.http.get<ResponseClub[]>(this.baseUrl );
  }

    // ✅ Add this method
  getClubById(id: number): Observable<ResponseClub> {
    return this.http.get<ResponseClub>(`${this.baseUrl}/${id}`);
  }

  // ✅ Create a new club
createClub(formData: FormData): Observable<ResponseClub> {
  return this.http.post<ResponseClub>(`${this.baseUrl}/register`, formData);
}


// In your club-services.ts or wherever the service is

uploadClubFiles(clubId: number, files: File[], type: string): Observable<any> {
  const formData = new FormData();
  files.forEach(file => {
    formData.append('files', file, file.name);
  });
  formData.append('type', type); // Add the credential type

  return this.http.post<any>(`${this.baseUrl}/${clubId}/files`, formData);
}

loadClubFiles(clubId: number): Observable<any> {
  return this.http.get<any>(`${this.baseUrl}/${clubId}/files`);
}
}