import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router'; // 👈 import Router
import { PlayerService } from '../../services/api/player/player-service';
import { PlayerResponce } from '../../representations/Player/playerResponce';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';
import { UserService } from '../../services/api/user/user-service';

@Component({
  selector: 'app-player',
  standalone: true,
  imports: [CommonModule, FormsModule, UserFilesComponent],
  templateUrl: './player-component.html',
  styleUrl: './player-component.css'
})
export class PlayerComponent implements OnInit {
  player: Partial<PlayerResponce> = {};
  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;
  positions: string[] = ['GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'FORWARD'];
  isEditModalOpen = false;
  editedPlayer: Partial<PlayerResponce> = {};
  profileCompletion = 0;

  constructor(
    private playerService: PlayerService,
    private cdr: ChangeDetectorRef,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPlayer();
  }

  loadPlayer(): void {
    this.loading = true;
    this.userService.currentUserId$.subscribe({
      next: (id) => {
        if (!id) {
          this.loading = false;
          return;
        }
        this.userService.getSelectedUser(id).subscribe({
          next: (data) => {
            this.player = data || {};
            this.calculateCompletion();
            this.loading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error(err);
            this.error = 'Failed to load player data';
            this.loading = false;
            this.cdr.detectChanges();
          }
        });
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }


  showSection(section: string) {
    this.activeSection = section;
  }

  calculateCompletion(): void {
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
    const filled = fields.filter(f => f != null && String(f).trim() !== '').length;
    this.profileCompletion = Math.round((filled / fields.length) * 100);
  }

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

  // ✅ Delete Player
  delete(): void {
    if (confirm(`Are you sure you want to delete ${this.player.firstName} ${this.player.lastName}?`)) {
      this.userService.deleteUser(this.player.id!).subscribe({
        next: () => {
          alert('Player deleted successfully.');
          this.router.navigate(['/club/players']); // 👈 redirect
        },
        error: (err) => {
          console.error('Failed to delete player:', err);
          alert('Failed to delete player. Please try again.');
        }
      });
    }
  }
}
