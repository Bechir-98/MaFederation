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
    this.initForm();
    this.loadRoles();
    this.loadPermissions();
  }

  loadRoles() {
    this.service.getRoles().subscribe(data => {
      this.roles = data;
      this.cd.detectChanges(); // ensure view updates
    });
  }

  loadPermissions() {
    this.service.getPermissions().subscribe(data => {
      this.permissions = data;
      this.cd.detectChanges(); // ensure view updates
    });
  }

  initForm() {
    this.roleForm = this.fb.group({
      name: [''],
      description: [''],
      permissionIds: this.fb.array([])
    });
  }

  openModal(role?: Role) {
    this.showModal = true;
    const permissionArray = this.roleForm.get('permissionIds') as FormArray;
    permissionArray.clear();

    if (role) {
      this.editingRoleId = role.id;
      this.roleForm.patchValue({
        name: role.name,
        description: role.description
      });
      role.permissions.forEach(p => permissionArray.push(this.fb.control(p.id)));
    } else {
      this.editingRoleId = null;
      this.roleForm.reset();
    }
  }

  closeModal() {
    this.showModal = false;
    this.roleForm.reset();
  }

  onPermissionChange(event: any, permissionId: number) {
    const permissionArray = this.roleForm.get('permissionIds') as FormArray;
    if (event.target.checked) {
      permissionArray.push(this.fb.control(permissionId));
    } else {
      const index = permissionArray.controls.findIndex(x => x.value === permissionId);
      permissionArray.removeAt(index);
    }
  }

  submitRole() {
    const roleData = {
      ...this.roleForm.value,
      permissions: (this.roleForm.value.permissionIds || []).map((id: number) => ({ id }))
    };

    if (this.editingRoleId) {
      this.service.updateRole(this.editingRoleId, roleData).subscribe(() => {
        this.loadRoles();
        this.closeModal();
      });
    } else {
      this.service.createRole(roleData).subscribe(() => {
        this.loadRoles();
        this.closeModal();
      });
    }
  }

  deleteRole(roleId: number) {
    if (confirm('Are you sure you want to delete this role?')) {
      this.service.deleteRole(roleId).subscribe(() => this.loadRoles());
    }
  }

  getPermissionNames(role: Role): string {
    if (!role.permissions || role.permissions.length === 0) return 'None';
    return role.permissions.map(p => p.name).join(', ');
  }
}
