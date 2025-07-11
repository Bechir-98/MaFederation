import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Nav } from './nav/nav';
import {UserNavComponent} from './User/user-nav-component/user-nav-component';
import { FormsModule } from '@angular/forms'; 


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Nav,UserNavComponent,FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
