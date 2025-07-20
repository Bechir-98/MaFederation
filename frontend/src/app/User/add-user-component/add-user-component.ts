import { Component } from '@angular/core';
import { UserRepresentation } from '../../representations/user-representation';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { MemberRepresentation } from '../../representations/member-representation';
@Component({
  selector: 'app-user-form',
  standalone: true,
  templateUrl: './add-user-component.html',
  styleUrls: ['./add-user-component.css'],
  imports: [CommonModule, FormsModule],
})
export class AddUserComponent {
  countries=COUNTRIES;
   clubs = [
    { id: 1, name: 'Club One' },
    { id: 2, name: 'Club Two' },
    // add your clubs here or fetch from backend
  ];
  
  user: MemberRepresentation= {
    userId: 0,
    clubId: 0,
    role: "",
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    nationality: '',
    gender: 'Male',  
    dateOfBirth: '',
    country: '',   
  };

    isClubMember(type: string): boolean {
    return ['Player', 'Staff', 'Admin'].includes(type);
  }

   
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
