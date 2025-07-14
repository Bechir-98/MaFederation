import { Component } from '@angular/core';
import { UserRepresentation } from '../../representations/user-representation';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-form',
  standalone: true,
  templateUrl: './add-user-component.html',
  styleUrls: ['./add-user-component.css'],
  imports: [CommonModule, FormsModule],
})
export class AddUserComponent {
  user: UserRepresentation = {
    userId: 0,
    clubId:0,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    city: '',
    postalCode: '',
    nationality: '',
    gender: 'Male',        // initialized to Male by default
    dateOfBirth: '',
    country: '',
    role: '',      
      // added status field
      // optional, empty string initially
  };

  previewUrl: string | ArrayBuffer | null = null;

  onPhotoSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => (this.previewUrl = reader.result);
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    console.log('User form submitted:', this.user);
    // Here you can add your service call to send `this.user` to backend
  }
}
