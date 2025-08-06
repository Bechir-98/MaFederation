import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from '../../../services/api/catergory/categories';
import { ResponseClub } from '../../../representations/Club/ResponseClub';
import { Category } from '../../../representations/Category/category';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-club-categories',
  standalone: true,
  templateUrl: './club-categories.html',
  styleUrls: ['./club-categories.css'],
  imports: [CommonModule]
  // Temporarily removed OnPush to test if that's the issue
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class ClubCategories implements OnInit {
  club: ResponseClub | null = null;
  clubId = 0;
  loadedCategories: Category[] = [];
  availableCategories: Category[] = [];
  addCategoryDialogOpen = false;
  showNotification = false;
  notificationMessage = '';

  constructor(
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.clubId = +id;
        this.loadCategories();
      }
    });
  }

  private loadCategories(): void {
    // Load both loadedCategories and all categories in parallel
    this.categoryService.getCategoriesByClubId(this.clubId).subscribe({
      next: (clubCategories) => {
        this.loadedCategories = [...clubCategories];

        this.categoryService.loadAllCategories().subscribe({
          next: (allCategories) => {
            const loadedIds = new Set(this.loadedCategories.map(c => c.id));
            this.availableCategories = allCategories.filter(cat => !loadedIds.has(cat.id));
            this.cdr.detectChanges();
          },
          error: err => console.error('Error loading all categories:', err)
        });

        this.cdr.detectChanges();
      },
      error: err => console.error('Error loading club categories:', err)
    });
  }

  openAddCategoryDialog(): void {
    this.addCategoryDialogOpen = true;
    // No need to load again here because we load availableCategories on init
  }

  addCategoryToClub(id: number): void {
    this.categoryService.addCategoryToClub(this.clubId, id).subscribe({
      next: () => {
        const category = this.availableCategories.find(c => c.id === id);
        if (category) {
          this.loadedCategories = [...this.loadedCategories, category];
          this.availableCategories = this.availableCategories.filter(c => c.id !== id);
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

  showCustomNotification(message: string): void {
    this.notificationMessage = message;
    this.showNotification = true;
    this.cdr.detectChanges();
    
    // Hide notification after 3 seconds
    setTimeout(() => {
      this.showNotification = false;
      this.cdr.detectChanges();
    }, 3000);
  }

  removeCategoryFromClub(categoryId: number): void {
    // Show confirmation dialog first
    const confirmDelete = confirm('Are you sure you want to delete this category?');
    
    if (confirmDelete) {
      // Show immediate feedback
      this.showCustomNotification('Deleting category...');
      
      this.categoryService.removeCategoryFromClub(this.clubId, categoryId).subscribe({
        next: () => {
          // Remove from loaded categories only after successful deletion
          const removedCategory = this.loadedCategories.find(c => c.id === categoryId);
          
          // Create new arrays to ensure change detection works
          this.loadedCategories = this.loadedCategories.filter(c => c.id !== categoryId);

          // Add back to available categories so user can re-add it
          if (removedCategory) {
            this.availableCategories = [...this.availableCategories, removedCategory].sort((a, b) => a.name.localeCompare(b.name));
          }

          // Force change detection immediately
          this.cdr.markForCheck();
          this.cdr.detectChanges();

          // Update notification to success
          this.showCustomNotification('Category deleted successfully!');
        },
        error: (err) => {
          console.error('Failed to remove category:', err);
          this.showCustomNotification('Failed to delete category. Please try again.');
        }
      });
    }
  }
}