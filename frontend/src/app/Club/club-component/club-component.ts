  import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { ActivatedRoute } from '@angular/router';
  import { ClubRepresentation } from '../../representations/Club/club-representation';
  import { ClubServices } from '../../services/api/club/club-services';
  import { CategoryService } from '../../services/api/catergory/categories';
  import { CategoryRepresentation } from '../../representations/Category/category-representation';

  @Component({
    selector: 'app-club-component',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './club-component.html',
    styleUrls: ['./club-component.css']
  })
  export class ClubComponent implements OnInit {

    categoryviewer = false;
    club: ClubRepresentation | null = null;
    clubId: number = 0;

    loadedCategories: CategoryRepresentation[] = [];

    constructor(
      private clubService: ClubServices,
      private categoryService: CategoryService,
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
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error loading club:', err);
        }
      });
    }

    toggleCategoryViewer(): void {
      this.categoryviewer = !this.categoryviewer;
      if (this.categoryviewer && this.loadedCategories.length === 0 && this.club?.categoryIds?.length) {
        this.loadCategories(this.club.categoryIds);
      }
    }

    loadCategories(categoryIds: number[]): void {
      this.categoryService.loadCategoriesByIds(categoryIds).subscribe({
        next: (data) => {
          this.loadedCategories = data;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error loading categories:', err);
        }
      });
    }

    // Fermer la fenêtre quand on clique en dehors du panneau
    onBackdropClick(event: MouseEvent) {
      this.categoryviewer = false;
      this.cdr.detectChanges();
    }

    // Fermer la fenêtre quand on appuie sur Échap
    @HostListener('window:keydown', ['$event'])
    handleKeyDown(event: KeyboardEvent) {
      if (event.key === 'Escape' && this.categoryviewer) {
        this.categoryviewer = false;
        this.cdr.detectChanges();
      }
    }
  }
