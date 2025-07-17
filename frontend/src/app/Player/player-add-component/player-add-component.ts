import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlayerRepresentation } from '../../representations/player-representation';
import { PlayerService } from '../../services/api/player/player-service';
import { CategoryRepresentation } from '../../representations/category-representation';

@Component({
  selector: 'app-player-add-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './player-add-component.html',
  styleUrls: ['./player-add-component.css']
})
export class PlayerAddComponent {
  step = 1;
  categories: CategoryRepresentation[] = [];
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  documents: { [key: string]: File } = {};

  player: PlayerRepresentation = {
    userId: 0,
    jerseyNumber: 0,
    height: 0,
    weight: 0,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    city: '',
    postalCode: '',
    licenseNumber: 0,
    dateOfBirth: '',
    country: '',
    role: '',
    gender: '',
    nationality: '',
    clubId: 0,
    categories: [],  // C’est une liste
  };

  constructor(
    private playerService: PlayerService,
    // private categoryService: CategoryService
  ) {}



}
