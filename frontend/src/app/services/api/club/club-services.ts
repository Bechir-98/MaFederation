import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {  ClubFile, ResponseClub } from '../../../representations/Club/ResponseClub';
import { StaffRepresentation } from '../../../representations/Staff/staffResponce';
import { PlayerRepresentation } from '../../../representations/Player/player-representation';
import { AdministrationRepresentation } from '../../../representations/Admin/adminstration-representation';


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



loadstaff(clubId: number): Observable<StaffRepresentation[]> {
  return this.http.get<StaffRepresentation[]>(`${this.baseUrl}/staff`, {
    params: { clubId: clubId.toString() }
  });
}

loadPlayers(clubId: number): Observable<PlayerRepresentation[]> {
  return this.http.get<PlayerRepresentation[]>(`${this.baseUrl}/players`, {
    params: { clubId: clubId.toString() }
  });

}

loadAdminstration ( clubId: number): Observable<AdministrationRepresentation[]> {
  return this.http.get<AdministrationRepresentation[]>(`${this.baseUrl}/players`, {
    params: { clubId: clubId.toString() }
  });

}





// In your club-services.ts or wherever the service is

uploadClubFiles(clubId: number, files: File[], type: string): Observable<any> {
  const formData = new FormData();
  files.forEach(file => {
    formData.append('file', file, file.name);
  });
  formData.append('type', type);

  return this.http.post<any>(`${this.baseUrl}/${clubId}/upload-file`, formData);
}


loadClubFiles(clubId: number): Observable<ClubFile[]> {
  return this.http.get<ClubFile[]>(`${this.baseUrl}/${clubId}/files`);
}

loadClubFileById(clubId: number, fileId: number): Observable<any> {
  return this.http.get<any>(`${this.baseUrl}/${clubId}/files/content`, {
    params: { id: fileId.toString() }
  });
}

deleteClubFile(clubId: number, fileId: number): Observable<void> {
  return this.http.delete<void>(`${this.baseUrl}/${clubId}/files`, {
    params: { fileId: fileId.toString() }
  });
}


updateClubFile(clubId: number, file: File, fileId: number): Observable<ClubFile> {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('fileId', fileId.toString()); // send fileId as param

  return this.http.put<ClubFile>(`${this.baseUrl}/${clubId}/files`, formData);
}







}