import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
import { Category } from '../../representations/Category/category';
import { PlayerService } from '../../services/api/player/player-service';
import { RegisterClubMember } from '../../representations/ClubMember/RegisterClubMember';
import { ClubServices } from '../../services/api/club/club-services';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { UserService } from '../../services/api/user/user-service';

@Component({
  selector: 'app-add-player-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-player-component.html',
  styleUrls: ['./add-player-component.css']
})
export class AddPlayerComponent implements OnInit {

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
    clubId: 0,
  };

  constructor(
    private clubService: ClubServices,
    private playerService: PlayerService,
    private userService: UserService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Fetch selected club from session
    this.clubService.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (club?.id) this.player.clubId = club.id;
        else console.warn('No club selected in session');
      },
      error: (err) => console.error('Failed to get selected club:', err)
    });
  }

  onProfilePicSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return this.resetProfilePic();

    const file = input.files[0];
    if (!file.type.startsWith('image/')) return this.resetProfilePic(true);

    this.profileInvalid = false;
    this.player.profilePicture = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.profilePreview = reader.result;
      this.cdr.detectChanges();
    };
    reader.readAsDataURL(file);
  }

  resetProfilePic(invalid: boolean = false): void {
    this.profileInvalid = invalid;
    this.profilePreview = null;
    this.player.profilePicture = null;
  }

  submitForm(): void {
    if (!this.player.clubId) return alert('No club selected. Cannot add player.');
    if (this.isSubmitting || this.profileInvalid) return alert('Please fix form errors before submitting.');

    this.isSubmitting = true;
    this.cdr.detectChanges();

    const { profilePicture, ...playerWithoutPic } = this.player;
    const formData = new FormData();
    formData.append('player', new Blob([JSON.stringify(playerWithoutPic)], { type: 'application/json' }));

    if (profilePicture) formData.append('profilePicture', profilePicture);

    this.playerService.createPlayer(formData).subscribe({
      next: (response) => {
        const newPlayerId = response.id;
        if (newPlayerId) {
          // ✅ Set current user via BehaviorSubject
          this.userService.setUserId(newPlayerId);

          this.isSubmitting = false;
          this.submitSuccess = true;
          this.cdr.detectChanges();
          this.router.navigate(['/club/players/profile']);
        } else {
          this.isSubmitting = false;
          this.cdr.detectChanges();
          console.warn('New player ID not found in response');
        }
      },
      error: (err) => {
        console.error('Error adding player:', err);
        this.isSubmitting = false;
        this.cdr.detectChanges();
      }
    });
  }
}
