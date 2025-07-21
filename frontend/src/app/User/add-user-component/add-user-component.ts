import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { UserRepresentation } from '../../representations/user-representation';

@Component({
  selector: 'app-user-form',
  standalone: true,
  templateUrl: './add-user-component.html',
  styleUrls: ['./add-user-component.css'],
  imports: [CommonModule, FormsModule],
})
export class AddUserComponent {
  countries = COUNTRIES;

  clubs = [
    { id: 1, name: 'Club One' },
    { id: 2, name: 'Club Two' },
  ];

  // Adapted to match UserRepresentation structure
  user: UserRepresentation & { userType: string; clubId?: number } = {
    userId: 0,
    email: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: 'Male',
    phoneNumber: '',
    address: '',
    nationality: '',
    country: '',
    nationalID: '',
    fileIds: [],
    userType: '',
    clubId: 0
  };

  selectedPhotoFile: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;

  isClubMember(type: string): boolean {
    return ['Player', 'Staff', 'Admin'].includes(type);
  }

  onPhotoSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedPhotoFile = file;
      const reader = new FileReader();
      reader.onload = () => (this.previewUrl = reader.result);
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    console.log('Submitting user:', this.user);

    // 1. Send user data (without photo)
    // this.userService.createUser(this.user).subscribe(...);

    // 2. Then upload photo separately (optional)
    if (this.selectedPhotoFile) {
      const formData = new FormData();
      formData.append('file', this.selectedPhotoFile);
      formData.append('type', 'PROFILE_PICTURE');
      formData.append('userId', this.user.userId.toString());

      // this.fileService.uploadUserFile(formData).subscribe(...);
    }
  }
}
