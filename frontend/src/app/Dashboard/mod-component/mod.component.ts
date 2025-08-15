import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';
import { ModDTO, ModService } from '../../services/api/mod/mod.service';

@Component({
  selector: 'app-mod-component',
  templateUrl: './mod.component.html',
  styleUrls: ['./mod.component.css'],
  standalone: true,
  imports: [CommonModule, UserFilesComponent]
})
export class ModComponent implements OnInit {
  mod!: ModDTO;
  activeSection: 'basic' | 'audit' | 'credentials' = 'basic';
  credentialsViewer = false;

  constructor(
    private route: ActivatedRoute,
    private modService: ModService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
  this.modService.getSelectedModerator().subscribe({
    next: (data: ModDTO) => {
      this.mod = data;
      this.cdr.detectChanges();
    },
    error: (err) => console.error('Failed to load selected moderator', err)
  });
}



  // Navigation methods
  editMod(): void {
    this.router.navigate(['/edit-mod'], { state: { mod: this.mod } });
  }

  deleteMod(): void {
    if (confirm('Are you sure you want to delete this moderator?')) {
      this.modService.deleteModerator(this.mod.id).subscribe(() => {
        alert('Moderator deleted.');
        this.router.navigate(['/mod-list']);
      });
    }
  }

  // Section switcher
  showSection(section: 'basic' | 'audit' | 'credentials'): void {
    this.activeSection = section;
    this.cdr.detectChanges();
  }

  // Credential modal toggle
  toggleCredentialViewer(): void {
    this.credentialsViewer = !this.credentialsViewer;
    this.cdr.detectChanges();
  }

  onBackdropClick(event: MouseEvent): void {
    this.credentialsViewer = false;
    this.cdr.detectChanges();
  }

  // Utility to display roles as comma-separated string
  getRolesString(mod: ModDTO): string {
    return mod.roles?.map(r => r.name).join(', ') || 'No Roles';
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.credentialsViewer) {
      this.credentialsViewer = false;
      this.cdr.detectChanges();
    }
  }
}
