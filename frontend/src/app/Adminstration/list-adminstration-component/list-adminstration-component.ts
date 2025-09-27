import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClubServices } from '../../services/api/club/club-services';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { UserService } from '../../services/api/user/user-service';

@Component({
  selector: 'app-list-administration-component',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './list-adminstration-component.html',
  styleUrls: ['./list-adminstration-component.css']
})
export class ListAdministrationComponent implements OnInit {

  admins: ResponceAdministration[] = [];
  filteredAdmins: ResponceAdministration[] = [];
  club: ResponseClub | null = null;
  validationFilter: 'all' | 'validated' | 'notValidated' = 'all';

  constructor(
    private clubServices: ClubServices,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clubServices.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (!club) return;
        this.club = club;
        this.loadAdmins();
      },
      error: (err) => console.error('Failed to get selected club:', err)
    });
  }

  loadAdmins(): void {
    if (!this.club) return;
    this.clubServices.loadAdministration().subscribe({
      next: (data: ResponceAdministration[]) => {
        this.admins = data;
        this.filterAdmins();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load administrators:', err)
    });
  }

  filterAdmins() {
    if (this.validationFilter === 'all') {
      this.filteredAdmins = [...this.admins];
    } else if (this.validationFilter === 'validated') {
      this.filteredAdmins = this.admins.filter(a => a.validated === "Validated");
    } else if (this.validationFilter === 'notValidated') {
      this.filteredAdmins = this.admins.filter(a => a.validated === "Rejected" || a.validated === null);
    }
  }

  viewAdmin(adminId: number): void {
    // Set selected user in UserService and navigate
    this.userService.setUserId(adminId);
    this.router.navigate(['/club/admins/profile']);
  }

  requestValidation(admin: ResponceAdministration) {
    if (!this.club) return;
    this.clubServices.requestMemberValidation(admin.id!, this.club.id!).subscribe({
      next: () => {
        alert(`${admin.firstName} ${admin.lastName} requested for verification.`);
        admin.validated = "Pending";
      },
      error: (err) => {
        console.error('Failed to request validation:', err);
        alert('Failed to request validation.');
      }
    });
  }
}
