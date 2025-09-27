import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { UserFilesComponent } from '../../files/user-files-component/user-files-component';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';
import { UserService } from '../../services/api/user/user-service';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';

@Component({
  selector: 'app-administration',
  standalone: true,
  imports: [CommonModule, UserFilesComponent, FormsModule],
  templateUrl: './administration-component.html',
  styleUrls: ['./administration-component.css']
})
export class AdministrationComponent implements OnInit {
  admin: Partial<ResponceAdministration> = {};
  editedAdmin: Partial<ResponceAdministration> = {};
  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;
  isEditModalOpen = false;
  profileCompletion = 0;

  constructor(
    private userService: UserService,
    private adminService: AdminstrationService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAdmin();
  }

  loadAdmin(): void {
    this.loading = true;

    // Subscribe to the selected user ID
    this.userService.currentUserId$.subscribe({
      next: (id) => {
        if (!id) {
          this.loading = false;
          return;
        }

        this.userService.getSelectedUser(id).subscribe({
          next: (data) => {
            this.admin = data || {};
            this.calculateProfileCompletion();
            this.loading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('getSelectedAdmin error', err);
            this.error = 'Failed to load administrator data';
            this.loading = false;
            this.cdr.detectChanges();
          }
        });
      }
    });
  }

  calculateProfileCompletion(): void {
    const fields = [
      this.admin.firstName,
      this.admin.lastName,
      this.admin.gender,
      this.admin.phoneNumber,
      this.admin.email,
      this.admin.address,
      this.admin.role
    ];
    const filled = fields.filter(f => f != null && String(f).trim() !== '').length;
    this.profileCompletion = Math.round((filled / fields.length) * 100);
  }

  showSection(section: string) {
    this.activeSection = section;
    this.cdr.detectChanges();
  }

  openEditModal(): void {
    this.editedAdmin = { ...this.admin };
    this.isEditModalOpen = true;
  }

  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  saveEditedAdmin(form: any): void {
    if (!this.editedAdmin.firstName || !this.editedAdmin.lastName) {
      alert('First name and Last name are required.');
      return;
    }

    const payload: Partial<ResponceAdministration> = { ...this.editedAdmin };

    this.adminService.updateAdmin(this.admin.id!, payload).subscribe({
      next: (updatedAdmin) => {
        this.admin = updatedAdmin;
        this.calculateProfileCompletion();
        this.closeEditModal();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('updateAdmin error', err);
        this.error = 'Failed to save administrator data';
      }
    });
  }

  deleteAdmin(): void {
    if (!confirm(`Are you sure you want to delete ${this.admin.firstName} ${this.admin.lastName}?`)) return;

    this.userService.deleteUser(this.admin.id!).subscribe({
      next: () => {
        alert('Administrator deleted successfully');
        this.router.navigate(['/club/admins']);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('deleteAdmin error', err);
        this.error = 'Failed to delete administrator';
      }
    });
  }

  getStatusClass(validated: string | boolean | null): string {
    switch (validated) {
      case true: return 'validated';
      case false: return 'rejected';
      default: return 'nonValidated';
    }
  }
}
