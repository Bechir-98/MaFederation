import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Nav } from './nav/nav';
import { PlayerComponent} from './Player/player-component/player-component';
import {UserComponent} from './User/user-component/user-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Nav, PlayerComponent,UserComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
