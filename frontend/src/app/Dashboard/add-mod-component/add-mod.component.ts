import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { COUNTRIES } from '../../representations/Countries';
import { ModService } from '../../services/api/mod/mod.service';
import { UserPost } from '../../representations/User/userPost';

@Component({
  selector: 'app-add-mod',
  standalone: true,
  templateUrl: './add-mod.component.html',
  styleUrls: ['./add-mod.component.css'],
  imports: [CommonModule, FormsModule],
})
export class AddModComponent {
  mod: Partial<UserPost> = {};
  countries = COUNTRIES;

  constructor(
    private modService: ModService,
    private router: Router
  ) {}

  addModerator(): void {
    if (!this.mod.firstName || !this.mod.lastName || !this.mod.email) {
      alert('Please fill in required fields');
      return;
    }

    // match backend field names
    const payload = {
      firstname: this.mod.firstName,
      lastname: this.mod.lastName,
      email: this.mod.email,
      password: 'aaaaaa', // or generate password if needed
      nationality: this.mod.nationality,
    };

    this.modService.createModerator(payload).subscribe({
      next: () => {
        alert(`Moderator ${this.mod.firstName} ${this.mod.lastName} added!`);
        this.router.navigate(['/mods']);
      },
      error: (err) => console.error('Failed to add moderator', err),
    });
  }
}
