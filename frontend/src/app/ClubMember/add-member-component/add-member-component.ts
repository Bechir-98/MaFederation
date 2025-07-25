import { Component, OnInit } from '@angular/core';
import { ClubMember } from '../../representations/ClubMember/ClubMember-representation';
import { ClubServices } from '../../services/api/club/club-services';
import { COUNTRIES } from '../../representations/Countries';
import { ClubRepresentation } from '../../representations/Club/club-representation';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-member-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-member-component.html',
  styleUrl: './add-member-component.css',
})
export class AddMemberComponent implements OnInit {

  countries = COUNTRIES;
  clubs: ClubRepresentation[] = [];
  step = false;

  member: ClubMember = {
    email: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    phoneNumber: '',
    address: '',
    nationalID: '',
    nationality: '',
    type: 'PLAYER', // or "STAFF", "ADMIN"
    clubId: 0,
    password: '', // Add password field
  };

  filesMap = new Map<string, File>();

  constructor(private clubservice: ClubServices) {}

  ngOnInit(): void {
    this.clubservice.loadClubs().subscribe({
      next: (res) => {
        this.clubs = res;
      },
    });
  }

  OnnextStep(): void {
    this.step = !this.step;
  }

  onFileSelect(event: Event, type: string): void {
    const input = event.target as HTMLInputElement;
    if (input && input.files && input.files.length > 0) {
      const file = input.files[0];
      this.filesMap.set(type, file);
    }
  }

  submitForm(): void {
    // Validate required fields
    if (!this.member.firstName || !this.member.lastName || !this.member.email) {
      alert('Please fill in all required fields (First Name, Last Name, Email)');
      return;
    }

    if (!this.member.clubId || this.member.clubId <= 0) {
      alert('Please select a valid club');
      return;
    }

    const formData = new FormData();

    // Ensure dateOfBirth is in correct ISO format for LocalDate parsing
    const memberData = {
      ...this.member,
      dateOfBirth: this.member.dateOfBirth ? 
        new Date(this.member.dateOfBirth).toISOString().split('T')[0] : 
        null
    };

    console.log('Sending member data:', memberData); // Debug log

    // Create a proper JSON blob for the user data
    const userBlob = new Blob(
      [JSON.stringify(memberData)],
      { type: 'application/json' }
    );
    formData.append('user', userBlob, 'user.json');

    // Only add files and file types if there are files selected
    if (this.filesMap.size > 0) {
      const filesArray: File[] = [];
      const fileTypesArray: string[] = [];

      this.filesMap.forEach((file, type) => {
        filesArray.push(file);
        fileTypesArray.push(type);
      });

      // Append files
      filesArray.forEach(file => {
        formData.append('files', file);
      });

      // Append file types
      fileTypesArray.forEach(type => {
        formData.append('fileTypes', type);
      });
    }

    this.clubservice.uploadMember(formData).subscribe({
      next: (res) => console.log('Success:', res),
      error: (err) => console.error('Error:', err),
    });
  }
}