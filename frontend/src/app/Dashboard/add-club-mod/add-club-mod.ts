import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClubServices } from '../../services/api/club/club-services';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { PostAdministration } from '../../representations/Admin/PostAdministration';
import {ModService} from '../../services/api/mod/mod.service';

@Component({
  selector: 'app-add-club-admin',
  standalone: true,
  templateUrl: './add-club-mod.html',
  styleUrls: ['./add-club-mod.css'],
  imports: [CommonModule, FormsModule],
})
export class AddClubAdminComponent implements OnInit {
  admin: Partial<PostAdministration> = {};
  clubs: ResponseClub[] = [];

  constructor(
    private modService: ModService,
    private clubService: ClubServices,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Load clubs to populate select dropdown
    this.clubService.loadClubs().subscribe({
      next: (data) => (this.clubs = data),
      error: (err) => console.error('Failed to load clubs', err),
    });
  }

  private generatePassword(length: number = 8): string {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    return Array.from({ length }, () => chars[Math.floor(Math.random() * chars.length)]).join('');
  }

  addAdmin(): void {
    if (!this.admin.firstName || !this.admin.lastName || !this.admin.email || !this.admin.clubId) {
      alert('Please fill in required fields');
      return;
    }

    // Auto-generate password
    const generatedPassword = this.generatePassword();

    // match backend field names
    const payload = {
      firstname: this.admin.firstName,
      lastname: this.admin.lastName,
      email: this.admin.email,
      // password: generatedPassword,
      password:"aaaaaa",
      clubId: this.admin.clubId,
    };

    this.modService.createClubAdmin(payload).subscribe({
      next: () => {
        // Backend returns void, so no createdAdmin object
        alert(`Club Admin ${this.admin.firstName} ${this.admin.lastName} added!`);
        this.router.navigate(['/admin/list']);
      },
      error: (err: any) => console.error('Failed to add admin', err),
    });
  }

}
