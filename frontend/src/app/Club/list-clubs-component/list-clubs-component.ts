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

  // Method to select a club by ID for super admin
  selectClub(clubId: number) {
    // Store selected club ID locally
    localStorage.setItem('selectedClubId', String(clubId));
    console.log(localStorage.getItem('selectedClubId'))

    // Navigate to club profile page
    this.router.navigate(['/admin/club/profile']);
  }
}
