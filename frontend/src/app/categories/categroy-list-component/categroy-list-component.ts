import { Component, Input, OnInit } from '@angular/core';
import { CategoryRepresentation } from '../../representations/category-representation';
import { CategoryService } from '../../services/api/catergory/categories';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categroy-list-component',
  imports: [CommonModule],
  templateUrl: './categroy-list-component.html',
  styleUrls: ['./categroy-list-component.css'],
  standalone: true
})
export class CategroyListComponent implements OnInit {

  @Input() categoryIds?: number[]; // ✅ correction ici

  categories: CategoryRepresentation[] = []; // les données à afficher

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    if (this.categoryIds && this.categoryIds.length > 0) {
      this.categoryService.loadCategoriesByIds(this.categoryIds).subscribe({
        next: (data) => {
          this.categories = data;
        },
        error: (err) => {
          console.error('Error loading categories:', err);
        }
      });
    } else {
      console.warn('No category IDs provided');
    }
  }
}
