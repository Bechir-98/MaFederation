import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClubServices } from '../../services/api/club/club-services';
import { StaffResponse } from '../../representations/Staff/staffResponce';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { UserService } from '../../services/api/user/user-service';

@Component({
  selector: 'app-list-staff-component',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './list-staff-component.html',
  styleUrls: ['./list-staff-component.css']
})
export class ListStaffComponent implements OnInit {

  Staffs: StaffResponse[] = [];
  filteredStaffs: StaffResponse[] = [];
  club: ResponseClub | null = null;
  validationFilter: 'all' | 'validated' | 'pending' | 'rejected' | 'nonValidated' = 'all';

  constructor(
    private clubservices: ClubServices,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clubservices.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (!club) return;
        this.club = club;
        this.loadStaff();
      },
      error: (err) => console.error('Failed to get selected club:', err)
    });
  }

  loadStaff(): void {
    if (!this.club) return;
    this.clubservices.loadStaff().subscribe({
      next: (data: StaffResponse[]) => {
        this.Staffs = data;
        this.filterStaff();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load staff:', err)
    });
  }

  filterStaff() {
    if (this.validationFilter === 'all') {
      this.filteredStaffs = [...this.Staffs];
    } else {
      this.filteredStaffs = this.Staffs.filter(s =>
        s.validated?.toLowerCase() === this.validationFilter.toLowerCase()
      );
    }
  }

  viewProfile(staffId: number) {
    // Set selected user in UserService and navigate
    this.userService.setUserId(staffId);
    this.router.navigate(['club/staff/profile']);
  }

  requestValidation(staff: StaffResponse) {
    if (!this.club) return;

    this.clubservices.requestMemberValidation(staff.id!, this.club.id!).subscribe({
      next: () => {
        alert(`${staff.firstName} ${staff.lastName} requested for verification.`);
        staff.validated = "Pending";
      },
      error: (err) => {
        console.error('Failed to request validation:', err);
        alert('Failed to request validation.');
      }
    });
  }
}
