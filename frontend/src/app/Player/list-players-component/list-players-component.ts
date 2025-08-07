import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ClubServices } from '../../services/api/club/club-services';
import { PlayerResponce } from '../../representations/Player/playerResponce';

@Component({
  selector: 'app-list-players-component',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './list-players-component.html',
  styleUrls: ['./list-players-component.css']
})
export class ListPlayersComponent implements OnInit {

  Players: PlayerResponce[] = [];
  clubId: number = 1;

  constructor(private clubservices: ClubServices, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.clubservices.loadPlayers(this.clubId).subscribe({
      next: (data: PlayerResponce[]) => {
        this.Players = data;
        this.cdr.detectChanges(); // optional
      },
      error: (err) => {
        console.error('Failed to load players:', err);
      }
    });
  }
}
