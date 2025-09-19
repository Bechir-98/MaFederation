import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { COUNTRIES } from '../../representations/Countries';
import {ModService,  } from '../../services/api/mod/mod.service';
import {UserPost} from '../../representations/User/userPost';


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

  constructor(private modService: ModService, private router: Router) {}

  addModerator(): void {
    if (!this.mod.firstName || !this.mod.lastName || !this.mod.email) {
      alert('Please fill in required fields');
      return;
    }

    // You should have a backend endpoint to create a moderator
    this.modService.createModerator(this.mod).subscribe({
      next: (createdMod) => {
        alert(`Moderator ${createdMod.firstName} ${createdMod.lastName} added!`);
        this.router.navigate(['/mods']);
      },
      error: (err) => console.error('Failed to add moderator', err)
    });
  }
}
