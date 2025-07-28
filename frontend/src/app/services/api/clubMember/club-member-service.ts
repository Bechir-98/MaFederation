import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClubMemberPost } from '../../../representations/ClubMember/ClubMemberPost';

@Injectable({
  providedIn: 'root'
})
export class ClubMemberService {


  private baseUrl: string = 'http://localhost:8080/';

   constructor(private http: HttpClient) {}



   public addClubMember (memberData: ClubMemberPost): Observable<ClubMemberPost> {
     return this.http.post<ClubMemberPost>(this.baseUrl + 'addmember', memberData);
   }



    public getMemberById(memberId: number): Observable<ClubMemberPost> {
      return this.http.get<ClubMemberPost>(`${this.baseUrl}/members/${memberId}`);
    }
}
