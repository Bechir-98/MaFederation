import { Component } from '@angular/core';
import { Category } from '../../representations/Category/category';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-category-component',
  standalone: true,
  imports: [FormsModule], 
  templateUrl: './add-category-component.html',
  styleUrls: ['./add-category-component.css']
})
export class AddCategoryComponent {

  category: Category = {
    id: 0,
    name: '',
    description: '',
    ageMin: 0,
    ageMax: 0
  };

  onSubmit() {
    console.log('Category submitted:', this.category);
  
  }
}
