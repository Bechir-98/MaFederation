import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClubMemberPost } from '../../../representations/ClubMember/ClubMemberPost';
import { ClubMemberResponse } from '../../../representations/ClubMember/ClubMemberResponce';

@Injectable({
  providedIn: 'root'
})
export class ClubMemberService {


  private baseUrl: string = 'http://localhost:8080/';

   constructor(private http: HttpClient) {}



   public addClubMember(formData: FormData): Observable<ClubMemberPost> {
  return this.http.post<ClubMemberPost>(this.baseUrl + 'addmember', formData);
}


  public getMemberById(memberId: number): Observable<ClubMemberResponse> {
      return this.http.get<ClubMemberResponse>(`${this.baseUrl}/members/${memberId}`);
  }


}
