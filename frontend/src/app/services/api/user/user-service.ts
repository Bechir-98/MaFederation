import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserFile, UserResponse } from '../../../representations/User/userResponse';
import { UserPost } from '../../../representations/User/userPost';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = "http://localhost:8080";

  // BehaviorSubject for reactive updates
  private userIdSource = new BehaviorSubject<number | null>(this.loadUserIdFromStorage());
  currentUserId$ = this.userIdSource.asObservable();

  constructor(private http: HttpClient) {}

  /** Set current user ID (updates BehaviorSubject and localStorage) */
  setUserId(id: number) {
    this.userIdSource.next(id);
    localStorage.setItem('currentUserId', id.toString());
  }

  /** Clear selected user */
  clearUserId() {
    this.userIdSource.next(null);
    localStorage.removeItem('currentUserId');
  }

  /** Load user ID from localStorage */
  private loadUserIdFromStorage(): number | null {
    const id = localStorage.getItem('currentUserId');
    return id ? +id : null;
  }

  /** Get user by ID */
  getSelectedUser(userId: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/user/${userId}`);
  }

  /** Get currently selected user (with fallback to BehaviorSubject/localStorage) */
  getCurrentUser(): Observable<UserResponse> {
    const currentId = this.userIdSource.value;
    if (!currentId) throw new Error('No user selected');
    return this.getSelectedUser(currentId);
  }

  /** Upload user files */
  uploadUserFiles(userId: number, files: File[], type: string): Observable<string> {
    const formData = new FormData();
    if (files.length > 0) {
      formData.append('file', files[0], files[0].name);
      formData.append('userId', userId.toString());
      formData.append('type', type);
    }
    return this.http.post(`${this.baseUrl}/userfiles/upload`, formData, { responseType: 'text' });
  }

  loadUserFiles(userId: number): Observable<UserFile[]> {
    return this.http.get<UserFile[]>(`${this.baseUrl}/userfiles/files`, { params: { userId: userId.toString() } });
  }

  loadUserFileById(fileId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/userfiles/file`, { params: { fileId: fileId.toString() } });
  }

  deleteUserFile(fileId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/userfiles/file/delete`, { params: { fileId: fileId.toString() } });
  }

  updateUserFile(file: File, fileId: number): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('fileId', fileId.toString());
    return this.http.put<any>(`${this.baseUrl}/userfiles/file/update`, formData);
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/clubs/delete/${userId}`);
  }
}
