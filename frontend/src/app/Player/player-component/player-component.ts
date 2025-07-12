import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerRepresentation } from '../../representations/player-representation';
import { PlayerService } from '../../services/api/player/player-service';

@Component({
  selector: 'app-player-component',
  imports: [CommonModule],
  standalone:true,
  templateUrl: './player-component.html',
  styleUrl: './player-component.css'
})
export class PlayerComponent implements OnInit {
  player: PlayerRepresentation | null = null;
  clubName: string = '';
  playerId: number = 0;

  constructor(
    private playerService: PlayerService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.playerId = +params['id'];
      if (this.playerId) {
        this.loadPlayer();
      }
    });
  }

  loadPlayer(): void {
    this.playerService.getPlayerById(this.playerId).subscribe({
      next: (player) => {
        this.player = player;
        this.loadClubName();
      },
      error: (error) => {
        console.error('Error loading player:', error);
      }
    });
  }

  loadClubName(): void {
    // You might want to create a ClubService to get club name by ID
    // For now, we'll just use a placeholder
    this.clubName = `Club ${this.player?.clubId}`;
  }

  getStatusTitle(): string {
    switch (this.player?.status) {
      case 'ACTIVE':
        return 'Player information validated';
      case 'PENDING':
        return 'Player information pending validation';
      case 'REJECTED':
        return 'Player information rejected';
      default:
        return 'Status unknown';
    }
  }

  getStatusIcon(): string {
    switch (this.player?.status) {
      case 'ACTIVE':
        return '✅';
      case 'PENDING':
        return '⚠️';
      case 'REJECTED':
        return '❌';
      default:
        return '❓';
    }
  }

  getStatusText(): string {
    switch (this.player?.status) {
      case 'ACTIVE':
        return 'Validated';
      case 'PENDING':
        return 'Pending Validation';
      case 'REJECTED':
        return 'Rejected';
      default:
        return 'Unknown';
    }
  }

  getStatusClass(): string {
    switch (this.player?.status) {
      case 'ACTIVE':
        return 'validated';
      case 'PENDING':
        return 'pending';
      case 'REJECTED':
        return 'rejected';
      default:
        return 'unknown';
    }
  }

  editPlayer(): void {
    this.router.navigate(['/edit-player', this.playerId]);
  }

  viewCredentials(): void {
    console.log('View credentials for player:', this.playerId);
    // Navigate to credentials page
  }

  viewStats(): void {
    console.log('View stats for player:', this.playerId);
    // Navigate to stats page
  }

  viewMedicalRecord(): void {
    console.log('View medical record for player:', this.playerId);
    // Navigate to medical record page
  }
}