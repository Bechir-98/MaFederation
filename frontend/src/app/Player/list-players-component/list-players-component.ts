import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ClubServices } from '../../services/api/club/club-services';
import { PlayerResponce } from '../../representations/Player/playerResponce';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/api/user/user-service';

type ValidationFilter = 'all' | 'pending' | 'validated' | 'rejected' | 'nonValidated';

@Component({
  selector: 'app-list-players-component',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './list-players-component.html',
  styleUrls: ['./list-players-component.css']
})
export class ListPlayersComponent implements OnInit {

  Players: PlayerResponce[] = [];
  filteredPlayers: PlayerResponce[] = [];
  club: ResponseClub | null = null;
  validationFilter: ValidationFilter = 'all';

  constructor(
    private clubservices: ClubServices,
    private cdr: ChangeDetectorRef,
    private UserService: UserService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.clubservices.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (!club) return;
        this.club = club;

        this.clubservices.loadPlayers().subscribe({
          next: (data: PlayerResponce[]) => {
            this.Players = data;
            this.filterPlayers();
            this.cdr.detectChanges();
          },
          error: (err) => console.error('Failed to load players:', err)
        });
      },
      error: (err) => console.error('Failed to get selected club:', err)
    });
  }

  filterPlayers() {
    if (this.validationFilter === 'all') {
      this.filteredPlayers = [...this.Players];
    } else {
      this.filteredPlayers = this.Players.filter(p => p.validated === this.validationFilter);
    }
  }

  viewProfile(playerId: number) {
    if (true)
    {
      this.UserService.setUserId(playerId);
      this.router.navigate(['club/players/profile'])
    }
    else
      alert('Failed to select player')
  }

  requestValidation(player: PlayerResponce) {
    if (!this.club) return;

    this.clubservices.requestMemberValidation(player.id!, this.club.id!).subscribe({
      next: () => {
        alert(`${player.firstName} ${player.lastName} requested for verification.`);
      },
      error: (err) => {
        console.error('Failed to request validation:', err);
        alert('Failed to request validation.');
      }
    });
  }
}
