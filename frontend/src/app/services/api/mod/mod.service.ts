  import { Injectable } from '@angular/core';
  import { HttpClient } from '@angular/common/http';
  import { Observable } from 'rxjs';

  export interface Role {
    id: number;
    name: string;
  }

  export interface ModDTO {
    id: number;
    email: string;
    profilePicture?: string;
    firstName: string;
    lastName: string;
    dateOfBirth?: string;
    gender?: string;
    phoneNumber?: string;
    address?: string;
    nationality?: string;
    nationalID?: string;
    roles: Role[];
    active?: boolean;
    createdAt?: string;
    updatedAt?: string;
    createdBy?: string;
    updatedBy?: string;
  }

  @Injectable({
    providedIn: 'root'
  })
  export class ModService {
    private baseUrl = 'http://localhost:8080/api/v1/management/**';

    constructor(private http: HttpClient) { }

    // Get all moderators
    getAllModerators(): Observable<ModDTO[]> {
      return this.http.get<ModDTO[]>(`${this.baseUrl}`);
    }

    // Get a moderator by ID
    getModeratorById(id: number): Observable<ModDTO> {
      return this.http.get<ModDTO>(`${this.baseUrl}/${id}`);
    }

    // Create a new moderator
    createModerator(mod: ModDTO): Observable<ModDTO> {
      return this.http.post<ModDTO>(`${this.baseUrl}`, mod);
    }

    // Select moderator in session
   // Select moderator in session
  selectModerator(moderatorId: number): Observable<void> {
  return this.http.post<void>(
    `${this.baseUrl}/select`,
    { moderatorId },
    { withCredentials: true }
  );
}


    // Get selected moderator from session
      getSelectedModerator(): Observable<ModDTO> {
        return this.http.get<ModDTO>(`${this.baseUrl}/selected`,{ withCredentials: true });
      }

    // Update moderator roles
    updateModeratorRoles(id: number, roles: Role[]): Observable<ModDTO> {
      return this.http.put<ModDTO>(`${this.baseUrl}/${id}/roles`, roles);
    }

    // Delete moderator
    deleteModerator(id: number): Observable<void> {
      return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
  }
