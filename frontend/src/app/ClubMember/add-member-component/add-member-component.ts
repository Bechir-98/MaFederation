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
import { Router } from '@angular/router';

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
    clubId: 0,
    passwordHash: '',
    categoryIds: [],
    profilePicture: null
  };

  constructor(
    private clubservice: ClubServices,
    private categoryService: CategoryService,
    private clubMemberService: ClubMemberService,
    private router: Router
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

  onClubChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const clubId = +select.value;
    this.member.clubId = clubId;
    
    if (clubId) {
      this.categoryService.getCategoriesByClubId(clubId).subscribe({
        next: (categories) => {
          this.categories = categories;
        },
        error: (err) => {
          console.error('Error fetching categories:', err);
        }
      });
    } else {
      this.categories = [];
    }
  }

  onUserTypeChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    this.member.type = select.value;
    
    // Clear categories when user type changes
    if (select.value !== 'STAFF') {
      this.member.categoryIds = [];
    }
  }

  onCategoryChange(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const categoryId = +checkbox.value;
    
    if (checkbox.checked) {
      if (!this.member.categoryIds.includes(categoryId)) {
        this.member.categoryIds.push(categoryId);
      }
    } else {
      this.member.categoryIds = this.member.categoryIds.filter(id => id !== categoryId);
    }
  }

  getSubmitButtonIcon(): string {
    if (this.isSubmitting) {
      return 'fa-spinner fa-spin';
    } else if (this.submitSuccess) {
      return 'fa-check';
    } else {
      return 'fa-plus';
    }
  }

  getSubmitButtonText(): string {
    if (this.isSubmitting) {
      return 'Creating Member...';
    } else if (this.submitSuccess) {
      return 'Member Created!';
    } else {
      return 'Create Member';
    }
  }

  submitForm(): void {
    if (this.isSubmitting) return;

    // Validate profile picture if needed
    if (this.profileInvalid) {
      alert('Please select a valid profile picture.');
      return;
    }

    this.isSubmitting = true;
    this.submitSuccess = false;

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
        this.isSubmitting = false;
        this.submitSuccess = true;
        
        // Show success state for 2 seconds, then navigate
        setTimeout(() => {
          this.router.navigate(['/clubs', response.clubId, 'members', response.userId]);

        }, 2000);
      },
      error: (err) => {
        console.error('Error adding member:', err);
        console.log(formData);
        this.isSubmitting = false;
        this.submitSuccess = false;
        alert('Failed to add member.');
      }
    });
  }

}