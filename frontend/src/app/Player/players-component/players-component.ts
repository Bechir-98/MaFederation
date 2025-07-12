import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PlayerRepresentation } from '../../representations/player-representation';
import { PlayerService } from '../../services/api/player/player-service';
import { PlayerCategoryRepresentation } from '../../representations/playercategory-represenation';

@Component({
  selector: 'app-players-component',
  imports: [CommonModule, RouterModule],
  standalone:true,
  templateUrl: './players-component.html',
  styleUrl: './players-component.css'
})
export class PlayersComponent implements OnInit {
  players: PlayerRepresentation[] = [];

  constructor(private playerService: PlayerService) { }

  ngOnInit(): void {
    this.loadPlayers();
  }

  loadPlayers(): void {
    this.playerService.getPlayers().subscribe({
      next: (players) => {
        this.players = players;
      },
      error: (error) => {
        console.error('Error loading players:', error);
      }
    });
  }

  getUniqueCategories(): PlayerCategoryRepresentation[] {
    const categories = this.players.map(player => player.category);
    return categories.filter((category, index, self) =>
      index === self.findIndex(c => c.categoryId === category.categoryId)
    );
  }

  viewPlayer(playerId: number): void {
    // Navigate to player detail view
    console.log('View player:', playerId);
  }

  editPlayer(playerId: number): void {
    // Navigate to player edit form
    console.log('Edit player:', playerId);
  }
  deletePlayer(playerId: number): void {
    if (confirm('Are you sure you want to delete this player?')) {
      this.playerService.deletePlayer(playerId).subscribe({
        next: () => {
          this.loadPlayers(); // Refresh the list
        },
        error: (error) => {
          console.error('Error deleting player:', error);
        }
      });
    }
  }
}