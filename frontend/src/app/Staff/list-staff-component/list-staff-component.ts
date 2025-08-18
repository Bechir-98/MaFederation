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

  // allow filtering by validation status
  validationFilter: 'all' | 'validated' | 'pending' | 'rejected' | 'nonValidated' = 'all';

  constructor(
    private clubservices: ClubServices,
    private UserService: UserService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clubservices.getSelectedClub().subscribe({
      next: (club: ResponseClub) => {
        if (!club) {
          console.warn('No club selected in session.');
          return;
        }
        this.club = club;

        this.clubservices.loadStaff().subscribe({
          next: (data: StaffResponse[]) => {
            this.Staffs = data;
            this.filterStaff();
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('Failed to load staff:', err);
          }
        });
      },
      error: (err) => {
        console.error('Failed to get selected club:', err);
      }
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
    this.UserService.selectUser(staffId).subscribe({
      next: () => {
        this.router.navigate(['club/staff/profile']);
      },
      error: err => {
        console.error('Failed to select staff', err);
      }
    });
  }

  requestValidation(staff: StaffResponse) {
    if (!this.club) return;

    this.clubservices.requestMemberValidation(staff.id!, this.club.id!).subscribe({
      next: () => {
        alert(`${staff.firstName} ${staff.lastName} requested for verification.`);
        staff.validated = "Pending"; // move staff to pending state
      },
      error: (err) => {
        console.error('Failed to request validation:', err);
        alert('Failed to request validation.');
      }
    });
  }
}
