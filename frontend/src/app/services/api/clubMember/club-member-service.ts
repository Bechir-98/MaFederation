import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClubMemberPost } from '../../../representations/ClubMember/ClubMemberPost';
import { ClubMemberResponse } from '../../../representations/ClubMember/ClubMemberResponce';

@Injectable({
  providedIn: 'root'
})
export class ClubMemberService {


  private baseUrl: string = 'http://localhost:8080';

   constructor(private http: HttpClient) {}



   public addClubMember(formData: FormData): Observable<ClubMemberResponse> {
  return this.http.post<ClubMemberResponse>(this.baseUrl + '/addmember', formData);
}


  public getMemberById(memberId: number,clubId:number): Observable<ClubMemberResponse> {
      return this.http.get<ClubMemberResponse>(`${this.baseUrl}/clubs/${clubId}/members/${memberId}`);
  }

  // Add to your ClubMemberService
deleteMember(memberId: number, clubId: number): Observable<any> {
  return this.http.delete(`/api/clubs/${clubId}/members/${memberId}`);
}

updateMemberCategories(memberId: number, clubId: number, categories: string[]): Observable<any> {
  return this.http.put(`/api/clubs/${clubId}/members/${memberId}/categories`, { categories });
}


}
