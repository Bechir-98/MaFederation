import { Component, ChangeDetectorRef, OnInit } from '@angular/core'; 
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';
import { PostAdministration } from '../../representations/Admin/postAdminustration';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { ClubServices } from '../../services/api/club/club-services';

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
    passwordHash: '',
    profilePicture: null,
    role: '',
    type: "ADMINSTRATOR",
    clubId: 0, // will be set after fetching selected club
    createdAt: '',
    createdBy: '',
    updatedAt: '',
    updatedBy: '',
    validated: false,
    validatedBy: "",
    validationDate: "" 
  };

  constructor(
    private clubService: ClubServices,
    private administrationService: AdminstrationService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Fetch selected club from session
    this.clubService.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        this.administration.clubId = club.id;
      },
      error: (err) => {
        console.error('No club selected in session', err);
      }
    });
  }

  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      this.resetProfilePic();
      this.cdr.detectChanges();
      return;
    }

    const file = input.files[0];
    if (!file.type.startsWith('image/')) {
      this.resetProfilePic(true);
      this.cdr.detectChanges();
      return;
    }

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
    formData.append('administration', new Blob(
      [JSON.stringify(adminWithoutPic)],
      { type: 'application/json' }
    ));

    if (profilePicture) {
      formData.append('profilePicture', profilePicture);
    }

    this.administrationService.createAdministration(formData).subscribe({
      next: (response) => {
        const newAdminId = response.id;
        if (newAdminId) {
          this.administrationService.selectAdministration(newAdminId).subscribe({
            next: () => {
              this.isSubmitting = false;
              this.submitSuccess = true;
              this.cdr.detectChanges();
              this.router.navigate(['/club/admins/profile']);
            },
            error: (err) => {
              this.isSubmitting = false;
              this.cdr.detectChanges();
              console.error('Failed to select admin:', err);
            }
          });
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
