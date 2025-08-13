import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { Category } from '../../representations/Category/category';
import { StaffPost } from '../../representations/Staff/staffPost';
import { CategoryService } from '../../services/api/catergory/categories';
import { StaffServices } from '../../services/api/staff/staff-services';

@Component({
  selector: 'app-add-staff-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-staff-component.html',
  styleUrls: ['./add-staff-component.css']
})
export class AddStaffComponent implements OnInit {

  countries = COUNTRIES;
  categories: Category[] = [];
  selectedCategories: number[] = [];

  step = false;
  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;
  isSubmitting = false;
  submitSuccess = false;

  staff: StaffPost = {
    email: '',
    profilePicture: null,
    passwordHash: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    phoneNumber: '',
    address: '',
    nationalID: '',
    nationality: '',
    type: 'STAFF',
    clubId: 0,
    categoryIds: [],
    specialty: '',
    createdAt: '',
    createdBy: '',
    updatedAt: '',
    updatedBy: '',
    validated: false,
    validatedBy: "",
    validationDate: "" 
  };

  constructor(
    private router: Router,
    private categoryService: CategoryService,
    private staffService: StaffServices,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const clubId = 1; // Get dynamically if possible
    this.staff.clubId = clubId;

    this.categoryService.getCategoriesByClubId(clubId).subscribe({
      next: (cats) => {
        this.categories = cats;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error loading categories', err)
    });
  }

  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      this.resetProfilePic();
      this.cdr.detectChanges();
      return;
    }

    const file = input.files[0];
    if (!file.type.startsWith('image/')) {
      this.resetProfilePic(true);
      this.cdr.detectChanges();
      return;
    }

    this.profileInvalid = false;
    this.staff.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
      this.cdr.detectChanges();
    };
    reader.readAsDataURL(file);
  }

  resetProfilePic(invalid: boolean = false): void {
    this.profileInvalid = invalid;
    this.profilePreview = null;
    this.staff.profilePicture = null;
  }

  toggleCategorySelection(categoryId: number): void {
    if (this.selectedCategories.includes(categoryId)) {
      this.selectedCategories = this.selectedCategories.filter(id => id !== categoryId);
    } else {
      this.selectedCategories.push(categoryId);
    }
    this.cdr.detectChanges();
  }

  submitForm(): void {
    if (this.isSubmitting || this.profileInvalid) {
      alert('Please fix form errors before submitting.');
      return;
    }

    if (!this.staff.specialty) {
      alert('Please select a specialty.');
      return;
    }

    this.isSubmitting = true;
    this.staff.categoryIds = this.selectedCategories;

    const { profilePicture, ...staffWithoutPic } = this.staff;
    const formData = new FormData();
    formData.append('staff', new Blob(
      [JSON.stringify(staffWithoutPic)],
      { type: 'application/json' }
    ));

    if (profilePicture) {
      formData.append('profilePicture', profilePicture);
    }

    this.staffService.createStaff(formData).subscribe({
      next: (response) => {
        const newStaffId = response.id;
        if (newStaffId) {
          // Just like player flow — select the created staff
          this.staffService.selectStaff(newStaffId).subscribe({
            next: () => {
              this.isSubmitting = false;
              this.submitSuccess = true;
              this.cdr.detectChanges();
              this.router.navigate(['/club/staff/profile']);
            },
            error: (err) => {
              this.isSubmitting = false;
              this.cdr.detectChanges();
              console.error('Failed to select staff:', err);
            }
          });
        } else {
          this.isSubmitting = false;
          this.cdr.detectChanges();
          console.warn('New staff ID not found in response');
        }
      },
      error: (err) => {
        console.error('Error adding staff:', err);
        this.isSubmitting = false;
        this.cdr.detectChanges();
      }
    });
  }
}
