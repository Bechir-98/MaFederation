import { Component, OnInit, ChangeDetectorRef,HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/api/user/user-service';
import { UserResponse } from '../../representations/User/userResponse';
import { CommonModule } from '@angular/common';
import {UserFilesComponent} from '../../files/user-files-component/user-files-component'

@Component({
  selector: 'app-user-component',
  templateUrl: './user-component.html',
  styleUrls: ['./user-component.css'],
  standalone: true,
  imports: [CommonModule,UserFilesComponent]
})
export class UserComponent implements OnInit {
  user!: UserResponse;
   credentialsViewer = false;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const userId = this.getUserIdSomehow();

   
      this.userService.getUserById(1).subscribe((data: UserResponse) => {
        this.user = data;
        this.cdr.detectChanges(); 
      });
    
  }

  editUser(): void {
    this.router.navigate(['/edit-user'], { state: { user: this.user } });
  }

  deleteUser(): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(this.user.id).subscribe(() => {
        alert('User deleted.');
        this.router.navigate(['/user-list']);
      });
    }
  }

  private getUserIdSomehow(): number | null {
    const nav = history.state;
    return nav?.userId ?? null;
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
    if (event.key === 'Escape') {
      if (this.credentialsViewer) {
        this.credentialsViewer = false;
        this.cdr.detectChanges();
      }
    }
  }




}

