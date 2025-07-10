import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Nav } from './nav/nav';
import { PlayerComponent} from './Player/player-component/player-component';
import {UserComponent} from './User/user-component/user-component';
import {StaffComponent} from './Staff/staff-component/staff-component';
import { ClubComponent } from './Club/club-component/club-component';
import { PlayersComponent} from './Player/players-component/players-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Nav,UserComponent,PlayersComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
