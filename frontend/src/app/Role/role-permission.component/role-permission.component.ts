import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { RolePermissionService, Permission, Role } from '../../services/api/Role/role-permission.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-role-permission',
  templateUrl: './role-permission.component.html',
  styleUrls: ['./role-permission.component.css'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class RolePermissionComponent implements OnInit {
  roles: Role[] = [];
  permissions: Permission[] = [];
  roleForm!: FormGroup;
  showModal: boolean = false;
  editingRoleId: number | null = null;

  constructor(
    private service: RolePermissionService,
    private fb: FormBuilder,
    private cd: ChangeDetectorRef  // <-- Inject ChangeDetectorRef
  ) {}

  ngOnInit(): void {

  }
}
