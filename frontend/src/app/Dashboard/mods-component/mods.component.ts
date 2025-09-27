import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ModService } from '../../services/api/mod/mod.service';
import { UserService } from '../../services/api/user/user-service';
import { UserResponse } from '../../representations/User/userResponse';

@Component({
  selector: 'app-mods-component',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './mods.component.html',
  styleUrls: ['./mods.component.css']
})
export class ModsComponent implements OnInit {
  moderators: UserResponse[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private modService: ModService,
    private userService: UserService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadModerators();
  }

  loadModerators(): void {
    this.loading = true;
    this.modService.getAllModerators().subscribe({
      next: (mods) => {
        this.moderators = mods;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Failed to load moderators', err);
        this.error = 'Failed to load moderators';
        this.loading = false;
        this.cdr.markForCheck();
      }
    });
  }

  viewModerator(mod: UserResponse): void {
    this.userService.setUserId(mod.id);
    this.router.navigate(['/admin/profile']);
  }
}
