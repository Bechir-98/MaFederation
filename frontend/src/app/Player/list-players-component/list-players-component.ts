import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ClubServices } from '../../services/api/club/club-services';
import { PlayerResponce } from '../../representations/Player/playerResponce';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-list-players-component',
  standalone: true,
  imports: [CommonModule, RouterModule,FormsModule],
  templateUrl: './list-players-component.html',
  styleUrls: ['./list-players-component.css']
})
export class ListPlayersComponent implements OnInit {

  Players: PlayerResponce[] = [];
  filteredPlayers: PlayerResponce[] = [];
  clubId: number = 1;

  validationFilter: 'all' | 'validated' | 'notValidated' = 'all'; // default filter

  constructor(private clubservices: ClubServices, private cdr: ChangeDetectorRef,
    private router: Router, private http: HttpClient
  ) {}

  ngOnInit(): void {
  this.clubservices.loadPlayers().subscribe({
    next: (data: PlayerResponce[]) => {
      this.Players = data;
      this.filterPlayers(); // <-- update filteredPlayers based on current filter
      this.cdr.detectChanges(); // optional
    },
    error: (err) => {
      console.error('Failed to load players:', err);
    }
  });
}


  filterPlayers() {
    if (this.validationFilter === 'all') {
      this.filteredPlayers = [...this.Players];
    } else if (this.validationFilter === 'validated') {
      this.filteredPlayers = this.Players.filter(p => p.validated === true);
    } else if (this.validationFilter === 'notValidated') {
      this.filteredPlayers = this.Players.filter(p => p.validated === false);
    }
  }

  viewProfile(playerId: number) {
    const baseUrl: string = 'http://localhost:8080';
    this.http.post(`${baseUrl}/players`, { playerId: playerId }, { withCredentials: true }).subscribe({
      next: () => {
        this.router.navigate(['club/players/profile']);
      },
      error: err => {
        console.error('Failed to select player', err);
      }
    });
  }

}
