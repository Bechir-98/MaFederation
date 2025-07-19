import { Component, Input, OnChanges, SimpleChanges, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryRepresentation } from '../../representations/category-representation';
import { CategoryService } from '../../services/api/catergory/categories';

@Component({
  selector: 'app-categroy-list-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categroy-list-component.html',
  styleUrls: ['./categroy-list-component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CategroyListComponent implements OnChanges {

  @Input() categoryIds?: number[];
  categories: CategoryRepresentation[] = [];

  constructor(
    private categoryService: CategoryService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
   
      
    }
  }

