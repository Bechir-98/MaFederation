import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService} from '../../../services/api/login/login.service';

@Component({
  selector: 'app-admin-nav',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './admin-nav.html',
  styleUrls: ['./admin-nav.css']
})
export class AdminNavComponent {

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout().subscribe();
  }
}
