import { Component } from '@angular/core';
import { UserRepresentation } from '../../representations/user-representation';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-form',
  standalone: true,
  templateUrl: './add-user-component.html',
  styleUrls: ['./add-user-component.css'],
  imports:[ CommonModule,FormsModule],
})
export class AddUserComponent {
  user: UserRepresentation = {
    userID: 0,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    city: '',
    postalCode: '',
    nationality: '',
    gender: '',
    dateOfBirth: '',
    country: '',
    role: 'USER',
    status: 'PENDING',
    rejectionReason: ''
  };

  previewUrl: string | ArrayBuffer | null = null;

  onPhotoSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => this.previewUrl = reader.result;
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    console.log('User form submitted:', this.user);
    // send data to service here
  }
}
