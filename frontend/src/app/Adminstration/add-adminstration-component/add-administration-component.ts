import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';
import { PostAdministration } from '../../representations/Admin/postAdminustration';

@Component({
  selector: 'app-add-administration-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-administration-component.html',
  styleUrls: ['./add-administration-component.css']
})
export class AddAdministrationComponent {

  countries = COUNTRIES;
  roles = ['ADMINISTRATOR', 'MANAGER', 'SECRETARY'];  // example roles

  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;
  isSubmitting = false;
  submitSuccess = false;

  administration :PostAdministration= {
    email: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    phoneNumber: '',
    address: '',
    nationalID: '',
    nationality: '',
    passwordHash: '',
    profilePicture: null,
    role: '',
    type:"ADMINSTRATOR",
    clubId: 1,  
    createdAt: '',
    createdBy: '',
    updatedAt: '',
    updatedBy: '',
    validated: false,
    validatedBy: "",
    validationDate: "" 
  };

  constructor(
    private administrationService: AdminstrationService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

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
    this.administration.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
      this.cdr.detectChanges();
    };
    reader.readAsDataURL(file);
  }

  resetProfilePic(invalid = false): void {
    this.profileInvalid = invalid;
    this.profilePreview = null;
    this.administration.profilePicture = null;
  }

 submitForm(): void {
  if (this.isSubmitting || this.profileInvalid) {
    alert('Please fix form errors before submitting.');
    return;
  }

  if (!this.administration.role) {
    alert('Please select a role.');
    return;
  }

  this.isSubmitting = true;
  this.cdr.detectChanges();

  const { profilePicture, ...adminWithoutPic } = this.administration;
  const formData = new FormData();
  formData.append('administration', new Blob(
    [JSON.stringify(adminWithoutPic)],
    { type: 'application/json' }
  ));

  if (profilePicture) {
    formData.append('profilePicture', profilePicture);
  }

  this.administrationService.createAdministration(formData).subscribe({
    next: (response) => {
      const newAdminId = response.id;
      if (newAdminId) {
        this.administrationService.selectAdministration(newAdminId).subscribe({
          next: () => {
            this.isSubmitting = false;
            this.submitSuccess = true;
            this.cdr.detectChanges();
            this.router.navigate(['/club/admins/profile']);
          },
          error: (err) => {
            this.isSubmitting = false;
            this.cdr.detectChanges();
            console.error('Failed to select admin:', err);
          }
        });
      } else {
        this.isSubmitting = false;
        this.cdr.detectChanges();
        console.warn('New admin ID not found in response');
      }
    },
    error: (err) => {
      this.isSubmitting = false;
      this.cdr.detectChanges();
      console.error('Error adding administration:', err);
    }
  });
}

}
