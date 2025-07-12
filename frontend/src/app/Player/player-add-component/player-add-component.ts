import { Component, OnInit } from '@angular/core';
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
export class PlayerAddComponent implements OnInit {
  step = 1;
  categories: CategoryRepresentation[] = [];
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  documents: { [key: string]: File } = {};

  constructor(
    private playerService: PlayerService,
    private categoryService: CategoryService
  ) {}

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
    role: 'PLAYER',
    status: 'PENDING',
    gender: '',
    nationality: '',
    clubId: 0,
    category: {
      playerId: 0,
      categoryId: 0,
      name: '',
    }
  };

  nextStep(): void {
    this.step = 2;
  }

  previousStep(): void {
    this.step = 1;
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      
      // Create image preview
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagePreview = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onDocumentSelected(event: any, documentType: string): void {
    const file = event.target.files[0];
    if (file) {
      this.documents[documentType] = file;
    }
  }

  onSubmit(): void {
    // Optional: Validate required fields
    if (!this.player.firstName || !this.player.lastName || !this.player.email) {
      alert('Please fill in all required fields: First Name, Last Name, Email.');
      return;
    }

    // Set club ID from localStorage
    this.player.clubId = Number(localStorage.getItem('clubId')) || 0;

    this.playerService.addPlayer(this.player).subscribe({
      next: (response) => {
        console.log('Player added successfully:', response);
        alert('Player form submitted successfully!');
        this.resetForm();
      },
      error: (error) => {
        console.error('Error adding player:', error);
        alert('Failed to submit player form.');
      }
    });
  }

  resetForm(): void {
    this.step = 1;
    this.selectedFile = null;
    this.imagePreview = null;
    this.documents = {};
    
    this.player = {
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
      role: 'PLAYER',
      status: 'PENDING',
      gender: '',
      nationality: '',
      clubId: 0,
      category: {
        playerId: 0,
        categoryId: 0,
        name: '',
      }
    };
  }
}