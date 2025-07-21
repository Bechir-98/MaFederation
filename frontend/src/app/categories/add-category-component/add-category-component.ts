import { Component } from '@angular/core';
import { CategoryRepresentation } from '../../representations/Category/category-representation';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-category-component',
  standalone: true,
  imports: [FormsModule], 
  templateUrl: './add-category-component.html',
  styleUrls: ['./add-category-component.css']
})
export class AddCategoryComponent {

  category: CategoryRepresentation = {
    categoryId: 0,
    name: '',
    description: '',
    ageMin: 0,
    ageMax: 0
  };

  onSubmit() {
    console.log('Category submitted:', this.category);
    // Ici tu peux appeler ton service pour envoyer la catégorie au backend
  }
}
