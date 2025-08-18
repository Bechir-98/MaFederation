import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { StaffResponse } from '../../representations/Staff/staffResponce';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';
import { UserService } from '../../services/api/user/user-service';
import { StaffServices } from '../../services/api/staff/staff-services';
import { Category } from '../../representations/Category/category';
import { ClubServices } from '../../services/api/club/club-services';
import { StaffPost } from '../../representations/Staff/staffPost';

@Component({
  selector: 'app-staff',
  standalone: true,
  imports: [CommonModule, FormsModule, UserFilesComponent],
  templateUrl: './staff-component.html',
  styleUrls: ['./staff-component.css']
})
export class StaffComponent implements OnInit {
  staff: Partial<StaffResponse> = {};
  editedStaff: Partial<StaffPost> = {};
  categories: Category[] = [];
  selectedCategories: number[] = [];

  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;
  isEditModalOpen = false;
  profileCompletion = 0;

  constructor(
    private userService: UserService,
    private staffService: StaffServices,
    private clubService: ClubServices,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadStaff();
  }

  loadStaff(): void {
    this.loading = true;
    this.userService.getSelectedUser().subscribe({
      next: (data) => {
        this.staff = data || {};
        this.loading = false;
        this.calculateCompletion();

        if (this.staff.clubId) {
          this.loadCategoriesForClub();
        }

        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = 'Failed to load staff data';
        this.loading = false;
        console.error('getSelectedStaff error', err);
        this.cdr.detectChanges();
      }
    });
  }

  private loadCategoriesForClub(): void {
    this.clubService.loadCategories().subscribe({
      next: (categories) => {
        this.categories = categories;

        // Pre-select staff's existing categories
        this.selectedCategories = this.staff.categories?.map(cat => cat.id) || [];

        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load categories', err)
    });
  }

  showSection(section: string) {
    this.activeSection = section;
  }

  openEditModal(): void {
    // Ensure categories are loaded before opening modal
    if (this.categories.length === 0 && this.staff.clubId) {
      this.loadCategoriesForClub();
    }

    // Pre-select staff's existing categories
    this.selectedCategories = this.staff.categories?.map(cat => cat.id) || [];

    // Copy staff data into editedStaff
    this.editedStaff = {
      firstName: this.staff.firstName,
      lastName: this.staff.lastName,
      phoneNumber: this.staff.phoneNumber,
      address: this.staff.address,
      email: this.staff.email,
      specialty: this.staff.specialty,
      clubId: this.staff.clubId,
      profilePicture: null, // file input
      categoryIds: [...this.selectedCategories] // pre-selected categories
    };

    this.isEditModalOpen = true;
    
    // Force change detection to ensure UI updates
    this.cdr.detectChanges();
  }

  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  toggleCategory(categoryId: number, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      if (!this.selectedCategories.includes(categoryId)) {
        this.selectedCategories.push(categoryId);
      }
    } else {
      this.selectedCategories = this.selectedCategories.filter(id => id !== categoryId);
    }
    
    // Update editedStaff.categoryIds to keep it in sync
    this.editedStaff.categoryIds = [...this.selectedCategories];
  }

  calculateCompletion(): void {
    const fields = [
      this.staff.firstName,
      this.staff.lastName,
      this.staff.phoneNumber,
      this.staff.address,
      this.staff.specialty,
      (this.staff.categories && this.staff.categories.length ? 'x' : null)
    ];
    const filled = fields.filter(f => f != null && String(f).trim() !== '').length;
    this.profileCompletion = Math.round((filled / fields.length) * 100);
  }

  saveEditedStaff(): void {
    if (!this.editedStaff.firstName || !this.editedStaff.lastName) {
      alert('First name and Last name are required.');
      return;
    }

    // Ensure categoryIds match selection
    this.editedStaff.categoryIds = [...this.selectedCategories];

    this.staffService.updateStaff(this.staff.id!, this.editedStaff).subscribe({
      next: () => {
        this.isEditModalOpen = false;
        this.loadStaff();
      },
      error: (err) => {
        console.error('Failed to save staff:', err);
        alert('Failed to save staff. Please try again.');
      }
    });
  }

  deleteStaff(): void {
    if (confirm(`Are you sure you want to delete ${this.staff.firstName} ${this.staff.lastName}?`)) {
      this.userService.deleteUser(this.staff.id!).subscribe({
        next: () => {
          alert('Staff deleted successfully.');
          this.router.navigate(['/club/staff']);
        },
        error: (err) => {
          console.error('Failed to delete staff:', err);
          alert('Failed to delete staff. Please try again.');
        }
      });
    }
  }
}