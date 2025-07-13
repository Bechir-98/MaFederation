import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ClubRepresentation } from '../../representations/club-representation';
import { ClubServices } from '../../services/api/club/club-services';

@Component({
  selector: 'app-club-component',
  imports: [CommonModule],
  templateUrl: './club-component.html',
  styleUrl: './club-component.css'
})
export class ClubComponent implements OnInit {
  club: ClubRepresentation | null = null;

  clubId: number = 0;
  

  constructor(
    private clubService: ClubServices,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.clubId =params['id'];
      console.log(this.clubId)        ;  
        if (this.clubId) {
        this.loadClub();
       
      }
    });
  }

loadClub(): void {
 

  this.clubService.getClubById(this.clubId).subscribe({
    next: (club) => {
      this.club = club;
      console.log('Club:', this.club);
    },
    error: (err) => {
      console.error('Erreur lors du chargement du club :', err);
  
    }
  });
}

  }




