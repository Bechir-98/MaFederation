import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../../representations/Category/category';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  /**
   * Fetch categories assigned to a specific club.
   * @param clubId - the ID of the logged-in club
   * @returns Observable of category array
   */

     createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.baseUrl}/createCategory`, category);
  }

  // Load all categories
  loadAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/Categories/AllCategories`);
  }

  // Get categories by IDs - note: backend expects a POST with a list of IDs in body
  getCategoriesByIds(ids: number[]): Observable<Category[]> {
    return this.http.post<Category[]>(`${this.baseUrl}/Categories/categories/byIds`, ids);
  }

 deleteCategory(id: number): Observable<void> {
  return this.http.request<void>('delete', `${this.baseUrl}/Categories`, { body: id });
}

updateCategory(category: Category): Observable<Category> {
  // Assuming your backend expects PUT on /categories with full updated category object
  return this.http.put<Category>(`${this.baseUrl}/categories`, category);
}



  
  getCategoriesByClubId(id :number): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/clubs/${id}/categories`);
  }

 addCategoryToClub(clubId: number, id: number): Observable<any> {
  return this.http.post(`${this.baseUrl}/clubs/${clubId}/categories`, { id });
}

removeCategoryFromClub(clubId: number, categoryId: number): Observable<any> {
  return this.http.delete(`${this.baseUrl}/clubs/${clubId}/categories/${categoryId}`);
}


}
