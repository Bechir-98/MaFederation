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
    type: '',
    clubId: 0,
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
    console.log('Moved to step:', this.step);
  }

  onFileSelect(event: Event, type: string): void {
    const input = event.target as HTMLInputElement;
    if (input && input.files && input.files.length > 0) {
      const file = input.files[0];
      this.filesMap.set(type, file);
      console.log(`Selected ${type}:`, file);
    }
  }

submitForm(): void {
  const formData = new FormData();
  formData.append('user', new Blob([JSON.stringify(this.member)], { type: 'application/json' }));

  this.filesMap.forEach((file, type) => {
    formData.append('files', file);
    formData.append('fileTypes', type);
  });

  this.clubservice.uploadMember(formData).subscribe({
    next: (res) => console.log('Success:', res),
    error: (err) => console.error('Error:', err),
  });
}

}
