import { Component ,OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlayerRepresentation } from '../../representations/player-representation';
import { PlayerService } from '../../services/api/player/player-service';
import { CategoryRepresentation } from '../../representations/category-representation';
import { CategoryService } from '../../services/api/catergory/categories';

@Component({
  selector: 'app-player-add-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './player-add-component.html',
  styleUrls: ['./player-add-component.css']
})
export class PlayerAddComponent  implements OnInit {
  step = 1;


constructor(
  private playerService: PlayerService,
  private categoryService: CategoryService // <-- ADD `private`
) {}
////////////////////////////////////////////////
categories: CategoryRepresentation[]= [];

 ngOnInit(): void {
    this.loadCategories();
  }

 loadCategories(): void {
  const clubId = Number(localStorage.getItem('clubId'));
  this.categoryService.loadCategoriesByClub(clubId).subscribe({
    next: (data) => {
      this.categories = data;
    },
    error: (err) => {
      console.error('Error loading categories:', err);
    }
  });
}

////////////////////////////////////////////////

  player: PlayerRepresentation = {
  playerId: 0,
  position: '',
  jerseyNumber: 0,
  height: 0,
  weight: 0,
  userID: 0,
  firstName: '',
  lastName: '',
  email: '',
  phoneNumber: '',
  address: '',
  city: '',
  postalCode: '',
  licenseNumber: '',
  dateOfBirth: '',
  country: '',
  role: 'Player',
  status: 'PENDING',
  gender: '',
  nationality: '',
  clubId: 0,
  category: {
    playerId: 0,
    categoryId: 0,
    name:"",
  }
};
////////////////////////////////////////////////

  nextStep() {
    this.step = 2;
  }
  previousStep() {
    this.step = 1;
  }
 ///////////////////////////////////////////////
  onSubmit() {
  // Optional: Validate required fields (simple example)
  if (!this.player.firstName || !this.player.lastName || !this.player.email) {
    alert('Please fill in all required fields: First Name, Last Name, Email.');
    return;
  }

    this.playerService.addPlayer(this.player).subscribe({
      next: (response) => {
        console.log('Player added successfully:', response);
        alert('Player form submitted successfully!');
      },
      error: (error) => {
        console.error('Error adding player:', error);
        alert('Failed to submit player form.');
      }
    });
  }
}

  




