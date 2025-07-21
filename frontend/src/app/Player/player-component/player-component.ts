import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerRepresentation } from '../../representations/Player/player-representation';
import { PlayerService } from '../../services/api/player/player-service';

@Component({
  selector: 'app-player-component',
  imports: [CommonModule],
  standalone:true,
  templateUrl: './player-component.html',
  styleUrl: './player-component.css'
})
export class PlayerComponent  {
 
  constructor(
    private playerService: PlayerService, 
  ) {}

  
 
}