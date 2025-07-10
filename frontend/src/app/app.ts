import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Nav } from './nav/nav';
import { PlayerComponent} from './Player/player-component/player-component';
import {UserComponent} from './User/user-component/user-component';
import {StaffComponent} from './Staff/staff-component/staff-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Nav, PlayerComponent,UserComponent,StaffComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
