import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-club-add-component',
  templateUrl: './add-club-component.html',
  styleUrls: ['./add-club-component.css'],
  imports:[ FormsModule,CommonModule]
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
    logoFile: null,
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
    console.log('Submitted club:', this.club);
    alert('Club registered successfully!');
    // You can now submit to a backend with FormData
  }
}
