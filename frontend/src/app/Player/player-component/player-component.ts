import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlayerService } from '../../services/api/player/player-service';
import { PlayerResponce } from '../../representations/Player/playerResponce';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';

@Component({
  selector: 'app-player',
  standalone: true,
  imports: [CommonModule, FormsModule, UserFilesComponent],
  templateUrl: './player-component.html',
  styleUrls: ['./player-component.css']
})
export class PlayerComponent implements OnInit {
  player: Partial<PlayerResponce> = {}; // allow partial while loading
  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;

  // modal / edit
  isEditModalOpen = false;
  editedPlayer: Partial<PlayerResponce> = {};

  // profile completion
  profileCompletion = 0;

  constructor(
    private playerService: PlayerService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadPlayer();
  }

  loadPlayer(): void {
    this.loading = true;
    this.playerService.getSelectedPlayer().subscribe({
      next: (data) => {
        this.player = data || {};
        this.loading = false;
        this.calculateCompletion();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to load player data';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  showSection(section: string) {
    this.activeSection = section;
    this.cdr.detectChanges();
  }

  /* ---------- Profile completion ---------- */
  calculateCompletion(): void {
    if (!this.player) {
      this.profileCompletion = 0;
      return;
    }

    const fields = [
      this.player.firstName,
      this.player.lastName,
      this.player.dateOfBirth,
      this.player.nationality,
      this.player.gender,
      this.player.phoneNumber,
      this.player.address,
      this.player.height,
      this.player.weight,
      this.player.jerseyNumber,
      this.player.position,
      (this.player.categories && this.player.categories.length ? 'x' : null)
    ];

    const filled = fields.filter(f => f !== null && f !== undefined && String(f).trim() !== '').length;
    this.profileCompletion = Math.round((filled / fields.length) * 100);
  }

  /* ---------- Edit modal ---------- */
  openEditModal(): void {
    this.editedPlayer = { ...this.player };
    this.isEditModalOpen = true;
  }

  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  saveEditedPlayer(form: any): void {
    if (!this.editedPlayer.firstName || !this.editedPlayer.lastName) {
      alert('First name and Last name are required.');
      return;
    }

    const payload: Partial<PlayerResponce> = { ...this.editedPlayer };
    if (payload.height) payload.height = Number(payload.height);
    if (payload.weight) payload.weight = Number(payload.weight);

    this.playerService.updatePlayer(this.player.id!, payload).subscribe({
      next: () => {
        this.isEditModalOpen = false;
        this.loadPlayer();
      },
      error: (err) => {
        console.error('Failed to save player:', err);
        alert('Failed to save player. Please try again.');
      }
    });
  }
}
