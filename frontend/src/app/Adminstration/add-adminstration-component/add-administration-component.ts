import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';
import { PostAdministration } from '../../representations/Admin/PostAdministration';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { ClubServices } from '../../services/api/club/club-services';
import { UserService } from '../../services/api/user/user-service';

@Component({
  selector: 'app-add-administration-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-administration-component.html',
  styleUrls: ['./add-administration-component.css']
})
export class AddAdministrationComponent implements OnInit {

  countries = COUNTRIES;
  roles = ['ADMINISTRATOR', 'MANAGER', 'SECRETARY'];

  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;
  isSubmitting = false;
  submitSuccess = false;

  administration: PostAdministration = {
    email: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    phoneNumber: '',
    address: '',
    nationalID: '',
    nationality: '',
    passwordHash: 'azertyuiop',
    profilePicture: null,
    role: '',
    clubId: 0,
    createdAt: '',
    createdBy: '',
    updatedAt: '',
    updatedBy: '',
  };

  constructor(
    private clubService: ClubServices,
    private administrationService: AdminstrationService,
    private userService: UserService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.clubService.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (club?.id) this.administration.clubId = club.id;
        else console.warn('No club selected in session');
      },
      error: (err) => console.error('Error fetching selected club:', err)
    });
  }

  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return this.resetProfilePic();

    const file = input.files[0];
    if (!file.type.startsWith('image/')) return this.resetProfilePic(true);

    this.profileInvalid = false;
    this.administration.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
      this.cdr.detectChanges();
    };
    reader.readAsDataURL(file);
  }

  resetProfilePic(invalid = false): void {
    this.profileInvalid = invalid;
    this.profilePreview = null;
    this.administration.profilePicture = null;
    this.cdr.detectChanges();
  }

  submitForm(): void {
    if (this.isSubmitting || this.profileInvalid) {
      alert('Please fix form errors before submitting.');
      return;
    }

    if (!this.administration.role) {
      alert('Please select a role.');
      return;
    }

    this.isSubmitting = true;
    this.cdr.detectChanges();

    const { profilePicture, ...adminWithoutPic } = this.administration;
    const formData = new FormData();
    formData.append('administration', new Blob([JSON.stringify(adminWithoutPic)], { type: 'application/json' }));

    if (profilePicture) formData.append('profilePicture', profilePicture);

    this.administrationService.createAdministration(formData).subscribe({
      next: (response) => {
        const newAdminId = response.id;
        if (newAdminId) {
          // ✅ Use BehaviorSubject to set current user ID
          this.userService.setUserId(newAdminId);

          this.isSubmitting = false;
          this.submitSuccess = true;
          this.cdr.detectChanges();
          this.router.navigate(['/club/admins/profile']);
        } else {
          this.isSubmitting = false;
          this.cdr.detectChanges();
          console.warn('New admin ID not found in response');
        }
      },
      error: (err) => {
        this.isSubmitting = false;
        this.cdr.detectChanges();
        console.error('Error adding administration:', err);
      }
    });
  }
}
