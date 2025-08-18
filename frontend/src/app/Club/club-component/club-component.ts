import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { ClubFiles } from '../../files/club-files-component/club-files-component';
import { ClubVerificationRequestDTO, ClubVerificationRequestService } from '../../services/api/verification/club/club-verification';

@Component({
  selector: 'app-club-component',
  standalone: true,
  imports: [CommonModule, ClubFiles],
  templateUrl: './club-component.html',
  styleUrls: ['./club-component.css']
})
export class ClubComponent implements OnInit {
  club: ResponseClub | null = null;
  isLoading = false;
  error: string | null = null;
  activeSection: string = 'basic';
  verificationRequested = false;
  credentialsViewer = false;

  constructor(
    private clubService: ClubServices,
    private verificationService: ClubVerificationRequestService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadSelectedClub();
    
  }

  private loadSelectedClub(): void {
    this.isLoading = true;
    this.error = null;

    this.clubService.getSelectedClub().subscribe({
      next: (club) => {
        if (!club) {
          this.error = 'No club selected.';
        } else {
          this.club = club;
          console.log(this.club)
        }
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading club:', err);
        this.error = 'Failed to load club information. Please try again.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  requestVerification(): void {
    if (!this.club) return;

    this.verificationService.requestVerification(this.club.id!).subscribe({
      next: (res: ClubVerificationRequestDTO) => {
        alert(`Verification request submitted for ${this.club?.name}`);
        this.verificationRequested = true;
      },
      error: (err) => {
        console.error('Failed to request verification:', err);
        alert('Failed to submit verification request.');
      }
    });
  }

  toggleCredentialViewer(): void {
    this.credentialsViewer = !this.credentialsViewer;
  }

  onBackdropClick(): void {
    this.credentialsViewer = false;
    this.cdr.detectChanges();
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.credentialsViewer) {
      this.credentialsViewer = false;
      this.cdr.detectChanges();
    }
  }

  onEditClub(): void {
    if (this.club) console.log('Edit club:', this.club.id);
  }

  onDeleteClub(): void {
    if (this.club && confirm(`Are you sure you want to delete ${this.club.name}?`)) {
      console.log('Delete club:', this.club.id);
    }
  }

  showSection(section: string): void {
    this.activeSection = section;
  }
}
