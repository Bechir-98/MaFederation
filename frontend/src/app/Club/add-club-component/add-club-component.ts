import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubRepresentation } from '../../representations/Club/club-representation';
import { CategoryRepresentation } from '../../representations/Category/category-representation';
import { CategoryService } from '../../services/api/catergory/categories';

@Component({
  selector: 'app-club-add-component',
  templateUrl: './add-club-component.html',
  standalone: true,
  styleUrls: ['./add-club-component.css'],
  imports: [FormsModule, CommonModule]
})
export class AddClubComponent implements OnInit {
  step = 1;

  categories: CategoryRepresentation[] = [];

  club: ClubRepresentation = {
    name: '',
    location: '',
    foundedYear: new Date().getFullYear(),
    contactEmail: '',
    contactPhone: '',
    bankAccount: '',
    bankName: '',
    categoryIds: [],      
    memberIds: [],
    files: {
      id: 0,
      licenseUrl: '',
      logoUrl: ''
    }
  };

  constructor(
    private clubservice: ClubServices,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.categoryService.loadAllCategories().subscribe({
      next: (response) => {
        this.categories = response;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  next(): void {
    this.step = 2;
  }

  onSubmit(): void {
    console.log('Submitted club:', this.club);

    this.clubservice.createClub(this.club).subscribe({
      next: () => alert('✅ Club registered successfully!'),
      error: (err) => {
        console.error('❌ Error creating club:', err);
        alert('❌ Failed to register club.');
      }
    });
  }

  onCategoryChange(category: CategoryRepresentation, event: any) {
    if (event.target.checked) {
      if (!this.club.categoryIds.includes(category.categoryId!)) {
        this.club.categoryIds.push(category.categoryId!);
      }
    } else {
      this.club.categoryIds = this.club.categoryIds.filter(
  (id: number) => id !== category.categoryId
  );

    }
  }
}
