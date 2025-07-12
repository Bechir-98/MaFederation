import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClubRepresentation } from '../../representations/club-representation';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubCardComponent} from  '../club-card-component/club-card-component';

@Component({
  selector: 'app-list-clubs-component',
  standalone: true,
  imports: [CommonModule,ClubCardComponent],
  templateUrl: './list-clubs-component.html',
  styleUrls: ['./list-clubs-component.css']
})
export class ListClubsComponent  {

  clubs: ClubRepresentation[] = [];
 public exist:boolean=false;

  constructor(private clubservice: ClubServices) {}
  ngOnInit(): void {
    this.clubservice.loadClubs().subscribe({
      next: (data) => {
        this.clubs = data;
        this.exist=false;
      },
      error: (err) => {
        console.error('Failed to load clubs:', err);
      }
    });
  }
}
