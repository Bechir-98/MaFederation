import {
  Component,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Category } from '../../representations/Category/category';
import { FormsModule, NgForm } from '@angular/forms';
import { CategoryService } from '../../services/api/catergory/categories';

@Component({
  selector: 'app-category-list-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categroy-list-component.html',
  styleUrls: ['./categroy-list-component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CategroyListComponent implements OnInit {
  loadedCategories: Category[] = [];
  addCategoryDialogOpen = false;
  showNotification = false;
  notificationMessage = '';
  isEditing = false;

  newCategory: Category = this.getEmptyCategory();

  constructor(
    private categoryService: CategoryService,
    private cdr: ChangeDetectorRef // Inject ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories() {
    this.categoryService.loadAllCategories().subscribe({
      next: (categories) => {
        this.loadedCategories = categories;
        this.cdr.markForCheck();  // Notify change detection
      },
      error: (err) => console.error('Error loading categories:', err),
    });
  }

  getEmptyCategory(): Category {
    return { id: 0, name: '', description: '', ageMin: 0, ageMax: 0 };
  }

  openAddCategoryDialog() {
    this.isEditing = false;
    this.newCategory = this.getEmptyCategory();
    this.addCategoryDialogOpen = true;
    this.cdr.markForCheck();
  }

  openEditCategoryDialog(category: Category) {
    this.isEditing = true;
    this.newCategory = { ...category }; // clone the selected category
    this.addCategoryDialogOpen = true;
    this.cdr.markForCheck();
  }

  closeDialog() {
    this.addCategoryDialogOpen = false;
    this.cdr.markForCheck();
  }

  saveCategory(form: NgForm) {
    if (form.invalid) {
      return;
    }
    if (this.isEditing) {
      this.categoryService.updateCategory(this.newCategory).subscribe({
        next: (updated) => {
          const index = this.loadedCategories.findIndex(c => c.id === updated.id);
          if (index > -1) this.loadedCategories[index] = updated;
          this.showNotificationMessage(`Category "${updated.name}" updated successfully!`);
          this.closeDialog();
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error updating category:', err);
        }
      });
    } else {
      this.categoryService.createCategory(this.newCategory).subscribe({
        next: (created) => {
          this.loadedCategories.push(created);
          this.showNotificationMessage(`Category "${created.name}" added successfully!`);
          this.closeDialog();
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error adding category:', err);
        }
      });
    }
  }

  deleteCategory(id?: number) {
    if (!id || !confirm('Are you sure you want to delete this category?')) return;
    this.categoryService.deleteCategory(id).subscribe({
      next: () => {
        this.loadedCategories = this.loadedCategories.filter(c => c.id !== id);
        this.showNotificationMessage('Category deleted successfully!');
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error deleting category:', err),
    });
  }

  showNotificationMessage(message: string) {
    this.notificationMessage = message;
    this.showNotification = true;
    this.cdr.markForCheck();
    setTimeout(() => {
      this.showNotification = false;
      this.cdr.markForCheck();
    }, 3000);
  }
}
  