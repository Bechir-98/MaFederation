import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserFilesComponent } from '../../files/user-files-component/user-files-component';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';
import { AdminstrationService } from '../../services/api/adminstration/adminstration-services';

@Component({
  selector: 'app-administration',
  standalone: true,
  imports: [CommonModule, UserFilesComponent],
  templateUrl: './administration-component.html',
  styleUrls: ['./administration-component.css']
})
export class AdministrationComponent implements OnInit {
  admin!: ResponceAdministration;
  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;

  constructor(
    private adminService: AdminstrationService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.adminService.getSelectedAdministration().subscribe({
      next: (data) => {
        this.admin = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Failed to load administrator data';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  showSection(section: string) {
    this.activeSection = section;
    this.cdr.detectChanges();
  }
}
