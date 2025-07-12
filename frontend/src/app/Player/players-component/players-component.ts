import { Component ,OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PlayerRepresentation } from '../../represantations/player-representation';
import { PlayerService } from '../../services/api/player/player-service';

@Component({
  selector: 'app-players-component',
  imports: [CommonModule,RouterModule],
  templateUrl: './players-component.html',
  styleUrl: './players-component.css'
})
export class PlayersComponent implements OnInit {

  players:PlayerRepresentation[] = [];

  constructor(private playerService: PlayerService) { }


  ngOnInit(): void {
      this.playerService.getPlayers().subscribe(players => {
          this.players = players;
      });
  }






}
