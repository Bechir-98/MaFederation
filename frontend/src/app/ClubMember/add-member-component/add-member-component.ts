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
    type: 'PLAYER', 
    clubId: 0,
    password: '', 
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
 

  submitForm(): void {
   
}



}