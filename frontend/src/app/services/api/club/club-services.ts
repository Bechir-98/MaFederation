import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostClub } from '../../../representations/Club/PostClub';
import { ResponseClub } from '../../../representations/Club/ResponseClub';


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
  createClub(club: PostClub): Observable<PostClub> {
    return this.http.post<PostClub>(`${this.baseUrl}/register`, club);
  }

  // uploadMember(memberData: FormData): Observable<any> {
  //   return this.http.post(this.baseUrl + '/addmember', memberData);
  // }
}
