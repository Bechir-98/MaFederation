import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ClubServices } from '../../services/api/club/club-services';
import { StaffRepresentation } from '../../representations/Staff/staffResponce';

@Component({
  selector: 'app-list-staff-component',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './list-staff-component.html',
  styleUrls: ['./list-staff-component.css']
})
export class ListStaffComponent implements OnInit {

  Staffs: StaffRepresentation[] = [];
  clubId: number = 1;
  private baseUrl: string = 'http://localhost:8080';

  constructor(
    private clubservices: ClubServices,
    private cdr: ChangeDetectorRef,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clubservices.loadStaff().subscribe({
      next: (data: StaffRepresentation[]) => {
        this.Staffs = data;
        console.log(data);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Failed to load staff:', err);
      }
    });
  }

  viewProfile(staffId: number) {
    this.http.post(`${this.baseUrl}/staff`, { staffId: staffId }, { withCredentials: true }).subscribe({
      next: () => {
        this.router.navigate(['club/staff/profile']);
      },
      error: err => {
        console.error('Failed to select staff', err);
      }
    });
  }
}
