import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';
import { ModService } from '../../services/api/mod/mod.service';
import { UserService } from '../../services/api/user/user-service';
import { UserResponse } from '../../representations/User/userResponse';

@Component({
  selector: 'app-mod-component',
  templateUrl: './mod.component.html',
  styleUrls: ['./mod.component.css'],
  standalone: true,
  imports: [CommonModule, UserFilesComponent]
})
export class ModComponent implements OnInit {
  mod: Partial<UserResponse> = {};
  activeSection: 'basic' | 'audit' | 'credentials' = 'basic';
  credentialsViewer = false;
  loading = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private modService: ModService,
    private userService: UserService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadModerator();
  }

  loadModerator(): void {
    this.loading = true;

    this.userService.currentUserId$.subscribe({
      next: (userId) => {
        if (!userId) {
          console.warn('No user selected');
          this.loading = false;
          return;
        }

        this.userService.getSelectedUser(userId).subscribe({
          next: (data) => {
            this.mod = data || {};
            this.loading = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('Failed to load selected moderator', err);
            this.error = 'Failed to load moderator';
            this.loading = false;
            this.cdr.detectChanges();
          }
        });
      }
    });
  }

  editMod(): void {
    this.router.navigate(['/edit-mod'], { state: { mod: this.mod } });
  }

  deleteMod(): void {
    if (!this.mod.id) return;
    if (!confirm('Are you sure you want to delete this moderator?')) return;

    this.userService.deleteUser(this.mod.id).subscribe({
      next: () => {
        alert('Moderator deleted.');
        this.router.navigate(['/mod-list']);
      },
      error: (err) => {
        console.error('Failed to delete moderator', err);
        this.error = 'Failed to delete moderator';
      }
    });
  }

  showSection(section: 'basic' | 'audit' | 'credentials'): void {
    this.activeSection = section;
    this.cdr.detectChanges();
  }

  toggleCredentialViewer(): void {
    this.credentialsViewer = !this.credentialsViewer;
    this.cdr.detectChanges();
  }

  onBackdropClick(event: MouseEvent): void {
    this.credentialsViewer = false;
    this.cdr.detectChanges();
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.credentialsViewer) {
      this.credentialsViewer = false;
      this.cdr.detectChanges();
    }
  }
}
