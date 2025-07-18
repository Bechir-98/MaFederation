import { Component, Input, OnInit } from '@angular/core';
import { CategoryRepresentation } from '../../representations/category-representation';
import { CategoryService } from '../../services/api/catergory/categories';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categroy-list-component',
  imports: [CommonModule],
  templateUrl: './categroy-list-component.html',
  styleUrl: './categroy-list-component.css',
  standalone: true
})
export class CategroyListComponent implements OnInit {

  @Input () clubId!:number;  
  categories:CategoryRepresentation[]=[];

   constructor(private categoryservice:CategoryService) {}
   ngOnInit(): void {
    if (this.clubId) {
      this.categoryservice.loadCategoriesByClub(this.clubId).subscribe({
        next: (data) => {
          this.categories = data;
        },
        error: (err) => {
          console.error('Error loading categories:', err);
        }
      });
    } else {
      console.warn('clubId is undefined');
    }
  }
}
