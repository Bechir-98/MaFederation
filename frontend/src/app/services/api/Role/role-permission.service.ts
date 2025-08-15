import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PermissionDTO {
  id: number;          // Only the ID is required for existing permissions
}

export interface RoleDTO {
  name: string;
  description?: string;
  permissions: PermissionDTO[];
}

export interface Permission {
  id: number;
  name: string;
  description?: string;
}

export interface Role {
  id: number;
  name: string;
  description?: string;
  permissions: Permission[];
}

@Injectable({
  providedIn: 'root'
})
export class RolePermissionService {
  private baseUrl = 'http://localhost:8080/roles';

  constructor(private http: HttpClient) {}

  // ----- Roles -----
  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.baseUrl);
  }

  getRole(id: number): Observable<Role> {
    return this.http.get<Role>(`${this.baseUrl}/${id}`);
  }

  createRole(role: RoleDTO): Observable<Role> {
    return this.http.post<Role>(this.baseUrl, role);
  }

  updateRole(roleId: number, role: RoleDTO): Observable<Role> {
    return this.http.put<Role>(`${this.baseUrl}/${roleId}`, role);
  }

  deleteRole(roleId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${roleId}`);
  }

  // ----- Permissions -----
  getPermissions(): Observable<Permission[]> {
    return this.http.get<Permission[]>(`${this.baseUrl}/permissions`);
  }

  createPermission(permission: Partial<Permission>): Observable<Permission> {
    return this.http.post<Permission>(`${this.baseUrl}/permissions`, permission);
  }

  deletePermission(permissionId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/permissions/${permissionId}`);
  }
}
