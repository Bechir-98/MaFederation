import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { Category } from '../../representations/Category/category';
import { PlayerService } from '../../services/api/player/player-service';
import { PlayerPost } from '../../representations/Player/PlayerPost';
import { RegisterClubMember } from '../../representations/ClubMember/RegisterClubMember';

@Component({
  selector: 'app-add-player-component',
  imports: [FormsModule, CommonModule],
  templateUrl: './add-player-component.html',
  styleUrl: './add-player-component.css'
})
export class AddPlayerComponent {

  countries = COUNTRIES;
  categories: Category[] = [];
  clubs: ResponseClub[] = [];

  step = false;
  profilePreview: string | ArrayBuffer | null = null;
  profileInvalid = false;
  isSubmitting = false;
  submitSuccess = false;

  player: RegisterClubMember= {
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
    type:"PLAYER",
    createdAt: "",
    createdBy: "",
    updatedAt: "",
    updatedBy: "",


    clubId: 1,
    
  };

  constructor(
    private playerService: PlayerService,
    private router: Router
  ) {}

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
    this.player.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
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
      console.log('Player added successfully:', response);
      this.isSubmitting = false;
      this.submitSuccess = true;

      // Assuming response contains the new player ID in response.id
      const newPlayerId = response.id;

      // Redirect to /club/players/{playerId}
      if (newPlayerId) {
        this.router.navigate(['/club/players', newPlayerId]);
      } else {
        console.warn('New player ID not found in response');
      }
    },
    error: (err) => {
      console.error('Error adding player:', err);
      this.isSubmitting = false;
    }
  });
}


}
