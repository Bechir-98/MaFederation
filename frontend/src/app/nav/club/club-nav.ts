import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService} from '../../services/api/login/login.service'; // adjust path

@Component({
  selector: 'app-club-nav',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './club-nav.html',
  styleUrls: ['./club-nav.css']
})
export class ClubNavComponent {

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout().subscribe();
  }
}
