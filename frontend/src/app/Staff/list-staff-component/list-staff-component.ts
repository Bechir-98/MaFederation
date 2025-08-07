import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
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

  constructor(
    private clubservices: ClubServices,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.clubservices.loadstaff(this.clubId).subscribe({
      next: (data: StaffRepresentation[]) => {
        this.Staffs = data;
        console.log(data);
        this.cdr.detectChanges(); // Force view update if necessary
      },
      error: (err) => {
        console.error('Failed to load staff:', err);
      }
    });
  }
}
