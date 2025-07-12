import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-club-add-component',
  templateUrl: './add-club-component.html',
  standalone:true,
  styleUrls: ['./add-club-component.css'],
  imports: [FormsModule, CommonModule]
})
export class AddClubComponent {
  step = 1;
  logoPreview: string | ArrayBuffer | null = null;

  club = {
    name: '',
    location: '',
    foundedYear: new Date().getFullYear(),
    contactEmail: '',
    contactPhone: '',
    bankAccount: '',
    bankName: '',
    logoFile: null as File | null,
    // Additional fields to match ClubRepresentation
    isValidatedByFederation: false,
    validatedAt: null as Date | null,
    validatedByUserId: null as number | null,
    rejectionReason: null as string | null,
    logoUrl: ''
  };

  nextStep() {
    this.step = 2;
  }

  onLogoSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.club.logoFile = file;

      const reader = new FileReader();
      reader.onload = () => {
        this.logoPreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    // Create FormData for file upload
    const formData = new FormData();
    
    // Add all form fields
    formData.append('name', this.club.name);
    formData.append('location', this.club.location);
    formData.append('foundedYear', this.club.foundedYear.toString());
    formData.append('contactEmail', this.club.contactEmail);
    formData.append('contactPhone', this.club.contactPhone);
    formData.append('bankAccount', this.club.bankAccount);
    formData.append('bankName', this.club.bankName);
    formData.append('isValidatedByFederation', this.club.isValidatedByFederation.toString());
    
    if (this.club.logoFile) {
      formData.append('logoFile', this.club.logoFile);
    }
    
    // Add optional fields if they have values
    if (this.club.validatedAt) {
      formData.append('validatedAt', this.club.validatedAt.toISOString());
    }
    if (this.club.validatedByUserId) {
      formData.append('validatedByUserId', this.club.validatedByUserId.toString());
    }
    if (this.club.rejectionReason) {
      formData.append('rejectionReason', this.club.rejectionReason);
    }
    if (this.club.logoUrl) {
      formData.append('logoUrl', this.club.logoUrl);
    }

    console.log('Submitted club:', this.club);
    console.log('FormData for backend:', formData);
    
    // Here you would typically send formData to your backend
    // Example: this.clubService.createClub(formData).subscribe(...)
    
    alert('Club registered successfully!');
  }
}