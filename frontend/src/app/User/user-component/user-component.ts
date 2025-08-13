import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/api/user/user-service';
import { UserResponse } from '../../representations/User/userResponse';
import { CommonModule } from '@angular/common';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';

@Component({
  selector: 'app-user-component',
  templateUrl: './user-component.html',
  styleUrls: ['./user-component.css'],
  standalone: true,
  imports: [CommonModule, UserFilesComponent]
})
export class UserComponent implements OnInit {
  user!: UserResponse;
  credentialsViewer = false;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const userId = Number(this.route.snapshot.paramMap.get('id')); // ✅ Get id from URL
    if (!isNaN(userId)) {
      this.userService.getUserById(userId).subscribe((data: UserResponse) => {
        this.user = data;
        this.cdr.detectChanges();
      });
    } else {
      console.error('Invalid user ID in URL');
    }
  }

  editUser(): void {
    this.router.navigate(['/edit-user'], { state: { user: this.user } });
  }

  deleteUser(): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(this.user.id!).subscribe(() => {
        alert('User deleted.');
        this.router.navigate(['/user-list']);
      });
    }
  }

  toggleCredentialViewer(): void {
    this.credentialsViewer = !this.credentialsViewer;
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
