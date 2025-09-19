  import { Injectable } from '@angular/core';
  import { HttpClient } from '@angular/common/http';
  import { Observable } from 'rxjs';
  import {ResponceAdministration} from '../../../representations/Admin/ResponceAdministration';
  import {PostAdministration} from '../../../representations/Admin/PostAdministration';
  import {UserResponse} from '../../../representations/User/userResponse';
  import {UserPost} from '../../../representations/User/userPost';

  @Injectable({
    providedIn: 'root'
  })
  export class ModService {
    private baseUrl = 'http://localhost:8080/api/v1';

    constructor(private http: HttpClient) { }


    getAllModerators(): Observable<UserResponse[]> {
      return this.http.get<UserResponse[]>(`${this.baseUrl}/management/getAllAdmins`);
    }


    getAllClubAdmins():Observable<Array<ResponceAdministration>>{
      return  this.http.get<Array<ResponceAdministration>>(`${this.baseUrl}/management/getAllClubsAdmins`)
    }


    // Create a new moderator
    createModerator(mod: Partial<UserPost>): Observable<UserResponse> {
      return this.http.post<UserResponse>(`${this.baseUrl}/auth/register`, mod);
    }

    createClubAdmin(admin: Partial<PostAdministration>): Observable<ResponceAdministration> {
      return this.http.post<ResponceAdministration>(`${this.baseUrl}/auth/registerClubAdmin`, admin);
    }




  }
