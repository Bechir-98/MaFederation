import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryRepresentation } from '../../../representations/category-representation';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = 'http://localhost:8080/Categories';

  constructor(private http: HttpClient) {}

  /**
   * Fetch categories assigned to a specific club.
   * @param clubId - the ID of the logged-in club
   * @returns Observable of category array
   */
  loadCategoriesByIds( clubCategories : number[]): Observable<any> {
    return this.http.get(`${this.baseUrl}/club/Categories}`);
  }

  loadAllCategories ( ): Observable<CategoryRepresentation[]>
  {
    return this.http.get<CategoryRepresentation[]>(this.baseUrl+"/AllCategories");
  }

  




}
