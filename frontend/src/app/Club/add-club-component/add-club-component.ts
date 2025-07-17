import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubRepresentation } from '../../representations/club-representation';

@Component({
  selector: 'app-club-add-component',
  templateUrl: './add-club-component.html',
  standalone: true,
  styleUrls: ['./add-club-component.css'],
  imports: [FormsModule, CommonModule]
})
export class AddClubComponent {
  step = 1;

  club: ClubRepresentation = {
    clubId: 0,
    name: '',
    location: '',
    foundedYear: new Date().getFullYear(),
    contactEmail: '',
    contactPhone: '',
    bankAccount: '',
    bankName: '',
    categories: [],
    members: [],
    files: {
  id: 0,
  licenseUrl: '',
  logoUrl: '',
  }};

  constructor(private clubservice: ClubServices) {}

  next() {
    this.step = 2;
  }

  onSubmit() {
    console.log('Submitted club:', this.club);

    this.clubservice.createClub(this.club).subscribe({
      next: () => alert('Club registered successfully!'),
      error: err => {
        console.error('Error creating club:', err);
        alert('Failed to register club.');
      }
    });
  }
}
