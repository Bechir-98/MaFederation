import { Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubCardComponent} from  '../club-card-component/club-card-component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-list-clubs-component',
  standalone: true,
  imports: [CommonModule, ClubCardComponent, RouterModule],
  templateUrl: './list-clubs-component.html',
  styleUrls: ['./list-clubs-component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush  // Optional: for better performance
})
export class ListClubsComponent implements OnInit {
  clubs: ResponseClub[] = [];

  constructor(
    private clubservice: ClubServices,
    private cdr: ChangeDetectorRef  // Add ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.clubservice.loadClubs().subscribe({
      next: (data) => {
        this.clubs = data;
        console.log(this.clubs);
        this.cdr.detectChanges(); // Force change detection
      },
      error: (err) => {
        console.error('Erreur de chargement des clubs:', err);
      }
    });
  }
}