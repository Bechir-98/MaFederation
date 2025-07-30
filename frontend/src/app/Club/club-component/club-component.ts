import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ClubServices } from '../../services/api/club/club-services';
import { Category } from '../../representations/Category/category';
import { ClubFile, ResponseClub } from '../../representations/Club/ResponseClub';
import { ClubFiles } from '../../files/club-files-component/club-files-component'

@Component({
  selector: 'app-club-component',
  standalone: true,
  imports: [CommonModule, ClubFiles],
  templateUrl: './club-component.html',
  styleUrls: ['./club-component.css']
})
export class ClubComponent implements OnInit {

  credentialsViewer = false;
  club: ResponseClub | null = null;
  clubId = 0;
  loadedCategories: Category[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(
    private clubService: ClubServices,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
   
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.clubId = +id;
        this.loadClub();
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

  toggleCredentialViewer(): void {
    this.credentialsViewer = !this.credentialsViewer;
  }

  onBackdropClick(event: MouseEvent): void {
    this.credentialsViewer = false;
    this.cdr.detectChanges();
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape') {
      if (this.credentialsViewer) {
        this.credentialsViewer = false;
        this.cdr.detectChanges();
      }
    }
  }

  // Additional helper methods for the enhanced template

  /**
   * Get validation status text using utility service
   */

  /**
   * Get CSS class for validation status using utility service
   */
 

  /**
   * Format date for display using utility service
   */
  
  /**
   * Format currency for display using utility service
   */
  
  /**
   * Get club summary information
   */


  /**
   * Check if club is ready for validation
   */


  /**
   * Get next steps for club completion
   */

  /**
   * Handle edit button click
   */
  onEditClub(): void {
    if (!this.club) return;
    
    // Navigate to edit page or open edit modal
    console.log('Edit club:', this.club.clubId);
    // TODO: Implement edit functionality
  }

  /**
   * Handle delete button click
   */
  onDeleteClub(): void {
    if (!this.club) return;
    
    const confirmed = confirm(`Are you sure you want to delete ${this.club.name}?`);
    if (confirmed) {
      console.log('Delete club:', this.club.clubId);
      // TODO: Implement delete functionality
      // this.clubService.deleteClub(this.club.clubId).subscribe(...)
    }
  }
}