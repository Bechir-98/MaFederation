import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ClubServices } from '../../services/api/club/club-services';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';
import { FormsModule } from '@angular/forms';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-list-adminstration-component',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './list-adminstration-component.html',
  styleUrls: ['./list-adminstration-component.css']
})
export class ListAdminstrationComponent implements OnInit {

  Admins: ResponceAdministration[] = [];
  selectedAdmin: ResponceAdministration | null = null;

  constructor(
    private clubservices: ClubServices,        // for loading all admins list
    private adminService: AdminstrationService, // for selected admin profile
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAdmins();
  }

  loadAdmins(): void {
    const clubId = 1; // or get dynamically if needed
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
    // Assuming you have a way to select admin (set session) before navigating
    this.adminService.selectAdministration(adminId).subscribe({
      next: () => this.router.navigate(['/club/admins/profile']),
      error: (err) => console.error('Failed to select administration', err)
    });
  }
}
