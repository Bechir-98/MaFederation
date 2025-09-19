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


}
