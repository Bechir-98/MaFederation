import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { Category } from '../../representations/Category/category';
import { PlayerService } from '../../services/api/player/player-service';
import { RegisterClubMember } from '../../representations/ClubMember/RegisterClubMember';

@Component({
  selector: 'app-add-player-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-player-component.html',
  styleUrls: ['./add-player-component.css']
})
export class AddPlayerComponent {

  countries = COUNTRIES;
  categories: Category[] = [];
  step = false;
  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;
  isSubmitting = false;
  submitSuccess = false;

  player: RegisterClubMember = {
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
    type: "PLAYER",
    createdAt: "",
    createdBy: "",
    updatedAt: "",
    updatedBy: "",
    clubId: 1,
    validated: false,
    validatedBy: "",
    validationDate: "" 
  };

  constructor(
    private playerService: PlayerService,
    private router: Router,
    private cdr: ChangeDetectorRef  // Inject ChangeDetectorRef here
  ) {}

  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      this.resetProfilePic();
      this.cdr.detectChanges();  // update UI immediately
      return;
    }

    const file = input.files[0];
    if (!file.type.startsWith('image/')) {
      this.resetProfilePic(true);
      this.cdr.detectChanges();
      return;
    }

    this.profileInvalid = false;
    this.player.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
      this.cdr.detectChanges(); // trigger view update after file loads
    };
    reader.readAsDataURL(file);
  }

  resetProfilePic(invalid: boolean = false): void {
    this.profileInvalid = invalid;
    this.profilePreview = null;
    this.player.profilePicture = null;
  }

  submitForm(): void {
    if (this.isSubmitting || this.profileInvalid) {
      alert('Please fix form errors before submitting.');
      return;
    }

    this.isSubmitting = true;
    this.cdr.detectChanges();

    const { profilePicture, ...playerWithoutPic } = this.player;
    const formData = new FormData();
    formData.append('player', new Blob(
      [JSON.stringify(playerWithoutPic)],
      { type: 'application/json' }
    ));

    if (profilePicture) {
      formData.append('profilePicture', profilePicture);
    }

    this.playerService.createPlayer(formData).subscribe({
      next: (response) => {
        const newPlayerId = response.id;
        if (newPlayerId) {
          this.playerService.selectPlayer(newPlayerId).subscribe({
            next: () => {
              this.isSubmitting = false;
              this.submitSuccess = true;
              this.cdr.detectChanges();
              this.router.navigate(['/club/players/profile']);
            },
            error: (err) => {
              this.isSubmitting = false;
              this.cdr.detectChanges();
              console.error('Failed to select player:', err);
            }
          });
        } else {
          this.isSubmitting = false;
          this.cdr.detectChanges();
          console.warn('New player ID not found in response');
        }
      },
      error: (err) => {
        this.isSubmitting = false;
        this.cdr.detectChanges();
        console.error('Error adding player:', err);
      }
    });
  }
}
