import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Nav } from './nav/nav';
import {UserComponent} from './User/user-component/user-component';
import { FormsModule } from '@angular/forms'; 


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Nav,UserComponent,FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
