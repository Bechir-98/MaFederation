import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { ClubServices } from '../../services/api/club/club-services';
import { CategoryService } from '../../services/api/catergory/categories';
import { Category } from '../../representations/Category/category';
import { ResponseClub } from '../../representations/Club/ResponseClub';

interface ClubCredential {
  type: string;    // e.g. 'INSURANCE_CERTIFICATE'
  content: string; // URL or base64 string to the document
}

@Component({
  selector: 'app-club-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './club-component.html',
  styleUrls: ['./club-component.css']
})
export class ClubComponent implements OnInit {

  credentialsViewer = false;

  club: ResponseClub | null = null;
  clubId = 0;

  loadedCategories: Category[] = [];
  loadedCredentials: ClubCredential[] = [];

  // Define required credential types here
  requiredCredentialTypes = ['INSURANCE_CERTIFICATE', 'LICENSE', 'TAX_DOCUMENT'];

  constructor(
    private clubService: ClubServices,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.clubId = +id;
        this.loadClub();
        
        this.loadClubFiles();
      }
    });
  }

  loadClub(): void {
    this.clubService.getClubById(this.clubId).subscribe({
      next: (club) => {
        this.club = club;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading club:', err);
      }
    });
  }

  loadClubFiles(): void {
    this.clubService.loadClubFiles(this.clubId).subscribe({
      next: (files: ClubCredential[]) => {
        this.loadedCredentials = files;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading credentials:', err);
      }
    });
  }

  uploadClubFile(file: File, type: string): void {
    this.clubService.uploadClubFiles(this.clubId, [file], type).subscribe({
      next: (files: ClubCredential[]) => {
        this.loadedCredentials = files;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error uploading club file:', err);
      }
    });
  }


  toggleCredentialViewer(): void {
    this.credentialsViewer = !this.credentialsViewer;
  }

  // Returns true if a credential of the given type exists
  hasCredential(type: string): boolean {
    return this.loadedCredentials.some(c => c.type === type);
  }

  // Handler to open file upload dialog (you'd implement the actual UI)
  openUploadDialog(type: string): void {
    // This could open a file input dialog or modal
    console.log(`Open upload dialog for credential type: ${type}`);
  }

  onBackdropClick(event: MouseEvent): void {
    this.credentialsViewer = false;
    this.cdr.detectChanges();
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape') {
      if ( this.credentialsViewer) {
      
        this.credentialsViewer = false;
        this.cdr.detectChanges();
      }
    }
  }
}
