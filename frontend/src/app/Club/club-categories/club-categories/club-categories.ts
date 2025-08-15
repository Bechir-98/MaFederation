import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../../services/api/catergory/categories';
import { ResponseClub } from '../../../representations/Club/ResponseClub';
import { Category } from '../../../representations/Category/category';
import { ClubServices } from '../../../services/api/club/club-services';

@Component({
  selector: 'app-club-categories',
  standalone: true,
  templateUrl: './club-categories.html',
  styleUrls: ['./club-categories.css'],
  imports: [CommonModule]
})
export class ClubCategories implements OnInit {
  club: ResponseClub | null = null;
  loadedCategories: Category[] = [];
  availableCategories: Category[] = [];
  addCategoryDialogOpen = false;
  showNotification = false;
  notificationMessage = '';

  loadingCategories = false; // NEW: track loading state

  constructor(
    private categoryService: CategoryService,  // For loading all categories
    private clubService: ClubServices,         // For club-specific categories
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Load selected club from session
    this.clubService.getSelectedClub().subscribe({
      next: (club) => {
        this.club = club;
        if (this.club) {
          this.loadCategories();
        }
      },
      error: (err) => {
        console.error('Error loading selected club:', err);
      }
    });
  }

  private loadCategories(): void {
    if (!this.club?.id) {
      console.warn('No club selected, cannot load categories');
      return;
    }

    this.loadingCategories = true;

    this.clubService.loadCategories().subscribe({
      next: (clubCategories) => {
        this.loadedCategories = [...clubCategories];

        this.categoryService.loadAllCategories().subscribe({
          next: (allCategories) => {
            const loadedIds = new Set(this.loadedCategories.map(c => c.id));
            this.availableCategories = allCategories.filter(cat => !loadedIds.has(cat.id));
            this.loadingCategories = false;
            this.cdr.detectChanges();
          },
          error: err => {
            console.error('Error loading all categories:', err);
            this.loadingCategories = false;
            this.cdr.detectChanges();
          }
        });

        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error loading club categories:', err);
        this.loadingCategories = false;
        this.cdr.detectChanges();
      }
    });
  }

  openAddCategoryDialog(): void {
    this.addCategoryDialogOpen = true;
  }

  addCategoryToClub(categoryId: number): void {
    if (!this.club?.id) return;

    this.clubService.addCategoryToClub(categoryId).subscribe({
      next: () => {
        const category = this.availableCategories.find(c => c.id === categoryId);
        if (category) {
          this.loadedCategories = [...this.loadedCategories, category];
          this.availableCategories = this.availableCategories.filter(c => c.id !== categoryId);
          this.cdr.detectChanges();
        }
        this.showCustomNotification('Category added successfully!');
      },
      error: (err) => {
        console.error('Failed to add category:', err);
        this.showCustomNotification('Failed to add category. Please try again.');
      }
    });
  }

  removeCategoryFromClub(categoryId: number): void {
    if (!this.club?.id) return;

    const confirmDelete = confirm('Are you sure you want to delete this category?');

    if (confirmDelete) {
      this.showCustomNotification('Deleting category...');

      this.clubService.removeCategoryFromClub(categoryId).subscribe({
        next: () => {
          const removedCategory = this.loadedCategories.find(c => c.id === categoryId);
          this.loadedCategories = this.loadedCategories.filter(c => c.id !== categoryId);

          if (removedCategory) {
            this.availableCategories = [...this.availableCategories, removedCategory].sort((a, b) =>
              a.name.localeCompare(b.name)
            );
          }

          this.cdr.markForCheck();
          this.cdr.detectChanges();

          this.showCustomNotification('Category deleted successfully!');
        },
        error: (err) => {
          console.error('Failed to remove category:', err);
          this.showCustomNotification('Failed to delete category. Please try again.');
        }
      });
    }
  }

  showCustomNotification(message: string): void {
    this.notificationMessage = message;
    this.showNotification = true;
    this.cdr.detectChanges();

    setTimeout(() => {
      this.showNotification = false;
      this.cdr.detectChanges();
    }, 3000);
  }
}
