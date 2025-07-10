import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-players-component',
  imports: [CommonModule],
  templateUrl: './players-component.html',
  styleUrl: './players-component.css'
})
export class PlayersComponent {

  Players:Array<any>=[] ;


}
