import { Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubCardComponent } from '../club-card-component/club-card-component';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-list-clubs-component',
  standalone: true,
  imports: [CommonModule, ClubCardComponent, RouterModule],
  templateUrl: './list-clubs-component.html',
  styleUrls: ['./list-clubs-component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListClubsComponent implements OnInit {
  clubs: ResponseClub[] = [];

  constructor(
    private clubservice: ClubServices,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit() {
    this.clubservice.loadClubs().subscribe({
      next: (data) => {
        this.clubs = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Erreur de chargement des clubs:', err);
      }
    });
  }

  // Method to select a club by ID and navigate
  selectClub(clubId: number) {
    this.clubservice.selectClub(clubId).subscribe({
      next: () => {

        // After storing clubId in session, navigate to club profile route
       this.router.navigate(['/admin/club/profile']);

      },
      error: (err) => {
        console.error('Failed to select club:', err);
        alert('Failed to select club.');
      }
    });
  }
}
