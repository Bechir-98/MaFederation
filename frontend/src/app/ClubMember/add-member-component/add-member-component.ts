import { Component, OnInit } from '@angular/core';
import { ClubMemberPost } from '../../representations/ClubMember/ClubMemberPost';
import { ClubServices } from '../../services/api/club/club-services';
import { COUNTRIES } from '../../representations/Countries';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Category } from '../../representations/Category/category';
import { CategoryService } from '../../services/api/catergory/categories';
import { ClubMemberService } from '../../services/api/clubMember/club-member-service';

@Component({
  selector: 'app-add-member-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-member-component.html',
  styleUrls: ['./add-member-component.css'], // fixed typo here
})
export class AddMemberComponent implements OnInit {
  countries = COUNTRIES;
  categories: Category[] = [];
  clubs: ResponseClub[] = [];
  step = false;

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
    clubId: 0,
    passwordHash: '',
    categoryIds: [],
    profilePicture: null
  };

  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;

  constructor(
    private clubservice: ClubServices,
    private categoryService: CategoryService,
    private clubMemberService: ClubMemberService
  ) {}

  ngOnInit(): void {
    this.clubservice.loadClubs().subscribe({
      next: (res) => {
        this.clubs = res;
      },
    });
  }

  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0];

      if (!file.type.startsWith('image/')) {
        this.profileInvalid = true;
        this.profilePreview = null;
        this.member.profilePicture = null;
        return;
      }

      this.profileInvalid = false;
      this.member.profilePicture = file;

      // Preview image
      const reader = new FileReader();
      reader.onload = () => {
        this.profilePreview = reader.result;
      };
      reader.readAsDataURL(file);
    } else {
      this.profileInvalid = true;
      this.profilePreview = null;
      this.member.profilePicture = null;
    }
  }

  onCategoryChange(event: any): void {
    const categoryId = +event.target.value;

    if (event.target.checked) {
      if (!this.member.categoryIds.includes(categoryId)) {
        this.member.categoryIds.push(categoryId);
      }
    } else {
      this.member.categoryIds = this.member.categoryIds.filter(id => id !== categoryId);
    }
  }

  onClubChange(event: any): void {
    const clubId = +event.target.value;

    this.member.clubId = clubId;
    this.categoryService.getCategoriesByClubId(clubId).subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (err) => {
        console.error('Error fetching categories:', err);
      }
    });
  }

  submitForm(): void {
    // Validate profile picture if needed
    if (this.profileInvalid) {
      alert('Please select a valid profile picture.');
      return;
    }

    // Prepare FormData to send member and profilePicture separately
    const { profilePicture, ...memberWithoutPic } = this.member;

    const formData = new FormData();
    formData.append('member', new Blob([JSON.stringify(memberWithoutPic)], { type: 'application/json' }));

    if (profilePicture) {
      formData.append('profilePicture', profilePicture);
    }

    this.clubMemberService.addClubMember(formData).subscribe({
      next: (response) => {
        console.log('Member added successfully:', response);
        alert('Member added!');
      },
      error: (err) => {
        console.error('Error adding member:', err);
        alert('Failed to add member.');
      }
    });
  }
}
