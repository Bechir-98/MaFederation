import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClubServices } from '../../services/api/club/club-services';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';
import { ResponseClub } from '../../representations/Club/ResponseClub';

@Component({
  selector: 'app-list-adminstration-component',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './list-adminstration-component.html',
  styleUrls: ['./list-adminstration-component.css']
})
export class ListAdminstrationComponent implements OnInit {

  Admins: ResponceAdministration[] = [];
  selectedAdmin: ResponceAdministration | null = null;
  club: ResponseClub | null = null;

  constructor(
    private clubservices: ClubServices,        // for loading all admins list
    private adminService: AdminstrationService, // for selecting admin profile
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Step 1: Get selected club from session
    this.clubservices.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (!club) {
          console.warn('No club selected in session.');
          return;
        }
        this.club = club;

        // Step 2: Load administration for the selected club
        this.loadAdmins();
      },
      error: (err) => {
        console.error('Failed to get selected club:', err);
      }
    });
  }

  loadAdmins(): void {
    this.clubservices.loadAdministration().subscribe({
      next: (data: ResponceAdministration[]) => {
        this.Admins = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Failed to load administrators:', err);
      }
    });
  }

  viewAdmin(adminId: number): void {
    // Set selected admin in session via backend before navigating
    this.adminService.selectAdministration(adminId).subscribe({
      next: () => this.router.navigate(['/club/admins/profile']),
      error: (err) => console.error('Failed to select administration', err)
    });
  }
}
    