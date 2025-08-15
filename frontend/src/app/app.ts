import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ClubNav } from './nav/club/club-nav';
import { AdminNav } from './nav/admin/admin-nav/admin-nav';
import {UserNavComponent} from './User/user-nav-component/user-nav-component';
import { FormsModule } from '@angular/forms'; 

// 
@Component({
  selector: 'app-root',
  imports: [RouterOutlet,UserNavComponent,FormsModule,AdminNav,ClubNav],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend';
}
