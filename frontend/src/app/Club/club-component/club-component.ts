import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ClubRepresentation } from '../../representations/club-representation';
import { ClubServices } from '../../services/api/club/club-services';
import { PlayerRepresentation } from '../../representations/player-representation';
import { PlayerService } from '../../services/api/player/player-service';

@Component({
  selector: 'app-club-component',
  imports: [CommonModule],
  templateUrl: './club-component.html',
  styleUrl: './club-component.css'
})
export class ClubComponent implements OnInit {
  club: ClubRepresentation | null = null;
  clubPlayers: PlayerRepresentation[] = [];
  clubId: number = 0;
  isLoading: boolean = true;
  error: string | null = null;

  constructor(
    private clubService: ClubServices,
    private playerService: PlayerService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.clubId = +params['id'];
      if (this.clubId) {
        this.loadClub();
        this.loadClubPlayers();
      }
    });
  }

  loadClub(): void {
    this.isLoading = true;
    this.error = null;
    
    this.clubService.getClubById(this.clubId).subscribe({
      next: (club) => {
        this.club = club;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading club:', error);
        this.error = 'Failed to load club information';
        this.isLoading = false;
      }
    });
  }

  loadClubPlayers(): void {
    this.playerService.getPlayersByClub(this.clubId).subscribe({
      next: (players) => {
        this.clubPlayers = players;
      },
      error: (error) => {
        console.error('Error loading club players:', error);
      }
    });
  }

  getValidationTitle(): string {
    return this.club?.isValidatedByFederation 
      ? 'Validated by Federation' 
      : 'Pending Federation Validation';
  }

  getValidationIcon(): string {
    return this.club?.isValidatedByFederation ? '✅' : '⚠️';
  }

  getValidationText(): string {
    return this.club?.isValidatedByFederation ? 'Validated' : 'Pending Validation';
  }

  getClubStatus(): string {
    if (!this.club) return 'Loading...';
    
    const status = this.club.isValidatedByFederation ? 'Active Club' : 'Pending Validation';
    return `${status} - Founded in ${this.club.foundedYear}`;
  }

  getValidationDetails(): string {
    if (!this.club) return 'N/A';
    
    if (this.club.isValidatedByFederation && this.club.validatedAt) {
      const validatedDate = new Date(this.club.validatedAt).toLocaleDateString();
      return `Federation Approved on ${validatedDate}`;
    } else if (this.club.rejectionReason) {
      return `Rejected: ${this.club.rejectionReason}`;
    } else {
      return 'Pending Federation Validation';
    }
  }

  editClub(): void {
    this.router.navigate(['/edit-club', this.clubId]);
  }

  deleteClub(): void {
    if (confirm('Are you sure you want to delete this club? This action cannot be undone.')) {
      this.clubService.deleteClub(this.clubId).subscribe({
        next: () => {
          this.router.navigate(['/clubs']);
        },
        error: (error) => {
          console.error('Error deleting club:', error);
          this.error = 'Failed to delete club';
        }
      });
    }
  }

  validateClub(): void {
    if (this.club && !this.club.isValidatedByFederation) {
      // You might want to get the current user ID from a user service
      const currentUserId = 1; // Replace with actual user ID
      
      this.clubService.validateClub(this.clubId, currentUserId).subscribe({
        next: (updatedClub) => {
          this.club = updatedClub;
        },
        error: (error) => {
          console.error('Error validating club:', error);
          this.error = 'Failed to validate club';
        }
      });
    }
  }

  rejectClub(): void {
    if (this.club && !this.club.isValidatedByFederation) {
      const rejectionReason = prompt('Enter rejection reason:');
      if (rejectionReason) {
        this.clubService.rejectClub(this.clubId, rejectionReason).subscribe({
          next: (updatedClub) => {
            this.club = updatedClub;
          },
          error: (error) => {
            console.error('Error rejecting club:', error);
            this.error = 'Failed to reject club';
          }
        });
      }
    }
  }

  viewCredentials(): void {
    console.log('View credentials for club:', this.clubId);
    this.router.navigate(['/club-credentials', this.clubId]);
  }

  viewCategories(): void {
    console.log('View categories for club:', this.clubId);
    this.router.navigate(['/club-categories', this.clubId]);
  }

  viewStadiumInfo(): void {
    console.log('View stadium info for club:', this.clubId);
    this.router.navigate(['/club-stadium', this.clubId]);
  }

  viewPlayers(): void {
    this.router.navigate(['/players'], { queryParams: { clubId: this.clubId } });
  }

  addPlayer(): void {
    this.router.navigate(['/add-player'], { queryParams: { clubId: this.clubId } });
  }

  refreshData(): void {
    this.loadClub();
    this.loadClubPlayers();
  }
}