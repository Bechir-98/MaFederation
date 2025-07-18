import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ClubRepresentation } from '../../representations/club-representation';
import { ClubServices } from '../../services/api/club/club-services';
import { CategroyListComponent } from '../../categories/categroy-list-component/categroy-list-component';

@Component({
  selector: 'app-club-component',
  standalone: true,
  imports: [CommonModule,CategroyListComponent],
  templateUrl: './club-component.html',
  styleUrls: ['./club-component.css']  
})
export class ClubComponent implements OnInit {

  categoryviewer=false;


  club: ClubRepresentation | null = null;
  clubId: number = 0;

  constructor(
    private clubService: ClubServices,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
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
    this.clubService.getClubById(this.clubId).subscribe({
      next: (club) => {
        this.club = club;
        console.log('Club loaded:', this.club);

        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading club:', err);
      }
    });
  }

  toggleCategoryViewer(): void {
  this.categoryviewer = !this.categoryviewer;
}


}
