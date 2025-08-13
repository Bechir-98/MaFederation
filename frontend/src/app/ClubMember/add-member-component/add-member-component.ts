import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { ClubMemberPost } from '../../representations/ClubMember/ClubMemberPost';
import { COUNTRIES } from '../../representations/Countries';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { Category } from '../../representations/Category/category';

import { ClubServices } from '../../services/api/club/club-services';
import { CategoryService } from '../../services/api/catergory/categories';
import { ClubMemberService } from '../../services/api/clubMember/club-member-service';

@Component({
  selector: 'app-add-member-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-member-component.html',
  styleUrls: ['./add-member-component.css']
})
export class AddMemberComponent implements OnInit {
  countries = COUNTRIES;
  categories: Category[] = [];
  clubs: ResponseClub[] = [];

  step = false;
  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;
  isSubmitting = false;
  submitSuccess = false;

  member: ClubMemberPost = {
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
    passwordHash: '',
    categoryIds: [],
    profilePicture: null,
    createdAt: '',
    createdBy: '',
    updatedAt: '',
    updatedBy: '',
    clubId: 1,
    validated: false,
    validatedBy: "",
    validationDate: ""  // Hardcoded for now since the club is logged in
  };

  constructor(
    private clubService: ClubServices,
    private clubMemberService: ClubMemberService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Load categories if needed
    // this.loadCategories();
  }


  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      this.resetProfilePic();
      return;
    }

    const file = input.files[0];
    if (!file.type.startsWith('image/')) {
      this.resetProfilePic(true);
      return;
    }

    this.profileInvalid = false;
    this.member.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
    };
    reader.readAsDataURL(file);
  }

  resetProfilePic(invalid: boolean = false): void {
    this.profileInvalid = invalid;
    this.profilePreview = null;
    this.member.profilePicture = null;
  }

  submitForm(): void {
    if (this.isSubmitting || this.profileInvalid) {
      alert('Please fix form errors before submitting.');
      return;
    }

    this.isSubmitting = true;

    const { profilePicture, ...memberWithoutPic } = this.member;
    const formData = new FormData();
    formData.append('member', new Blob([JSON.stringify(memberWithoutPic)], { type: 'application/json' }));
    if (profilePicture) {
      formData.append('profilePicture', profilePicture);
    }

    this.clubMemberService.addClubMember(formData).subscribe({
      next: (response) => {
        console.log('Member added successfully:', response);
        this.isSubmitting = false;
        this.submitSuccess = true;

        setTimeout(() => {
          this.router.navigate(['/club/members',response?.id || '']);
        }, 2000);
      },
      error: (err) => {
        console.error('Error adding member:', err);
        this.isSubmitting = false;
        alert('Failed to add member.');
      }
    });
  }
}
