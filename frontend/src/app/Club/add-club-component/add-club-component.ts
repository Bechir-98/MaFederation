import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubRepresentation } from '../../representations/club-representation';

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


  public constructor (private clubservice :ClubServices){
  } 


  club :ClubRepresentation= {
    name: '',
    clubId:0,
  
    location: '',
    foundedYear: new Date().getFullYear(),
    contactEmail: '',
    contactPhone: '',
    bankAccount: '',
    bankName: '',
    // Additional fields to match ClubRepresentation
    logoUrl: ''
  };

  nextStep() {
    this.step = 2;
  }

  onLogoSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.club.logoUrl = file;

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
    
    
    if (this.club.logoUrl) {
      formData.append('logoFile', this.club.logoUrl);
    }
  
    console.log('Submitted club:', this.club);
 
    
    // Here you would typically send formData to your backend
    // Example: this.clubService.createClub(formData).subscribe(...)

      this.clubservice.createClub(this.club)
    
    alert('Club registered successfully!');
  }
}