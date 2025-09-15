import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserFile, UserResponse } from '../../../representations/User/userResponse';
import { UserPost } from '../../../representations/User/userPost';

@Injectable({
  providedIn: 'root'
})
export class UserService {
     private baseUrl: string = "http://localhost:8080";

    constructor(private http: HttpClient) {}


  selectUser(userId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/user/select`, { userId },{ withCredentials: true });
  }

  getSelectedUser(): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/user/profile`,{ withCredentials: true });
  }


uploadUserFiles(userId: number, files: File[], type: string): Observable<string> {
  const formData = new FormData();
  if (files.length > 0) {
    formData.append('file', files[0], files[0].name);
    formData.append('userId', userId.toString());
    formData.append('type', type);
  }
  return this.http.post(`${this.baseUrl}/userfiles/upload`, formData, {
    responseType: 'text'  // <-- fix here
  });
}

loadUserFiles(userId: number): Observable<UserFile[]> {
  return this.http.get<UserFile[]>(`${this.baseUrl}/userfiles/files`, {
    params: { userId: userId.toString() }
  });
}

loadUserFileById(fileId: number): Observable<any> {
  return this.http.get<any>(`${this.baseUrl}/userfiles/file`, {
    params: { fileId: fileId.toString() }
  });
}

deleteUserFile(fileId: number): Observable<void> {
  return this.http.delete<void>(`${this.baseUrl}/userfiles/file/delete`, {
    params: { fileId: fileId.toString() }
  });
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
