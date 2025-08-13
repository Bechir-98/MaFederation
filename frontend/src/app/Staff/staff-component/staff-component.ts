import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaffServices } from '../../services/api/staff/staff-services';
import { StaffRepresentation } from '../../representations/Staff/staffResponce';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';

@Component({
  selector: 'app-staff',
  standalone: true,
  imports: [CommonModule, UserFilesComponent],
  templateUrl: './staff-component.html',
  styleUrls: ['./staff-component.css']
})
export class StaffComponent implements OnInit {
  staff!: StaffRepresentation;
  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;

  constructor(
    private staffService: StaffServices,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.staffService.getSelectedStaff().subscribe({
      next: (data) => {
        this.staff = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = 'Failed to load staff data';
        this.loading = false;
        console.error('getSelectedStaff error', err);
        this.cdr.detectChanges();
      }
    });
  }

  showSection(section: string) {
    this.activeSection = section;
    this.cdr.detectChanges();
  }
}
