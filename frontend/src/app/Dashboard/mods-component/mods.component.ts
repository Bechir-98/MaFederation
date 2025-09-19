import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ModService } from '../../services/api/mod/mod.service';
import { FormsModule } from '@angular/forms';
import {UserResponse} from '../../representations/User/userResponse';
import {UserService} from '../../services/api/user/user-service';

@Component({
  selector: 'app-mods-component',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './mods.component.html',
  styleUrls: ['./mods.component.css']
})
export class ModsComponent implements OnInit {

  moderators: UserResponse[] = [];

  constructor(
    private modService: ModService,
    private userService: UserService  ,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadModerators();
  }

  loadModerators(): void {
    this.modService.getAllModerators().subscribe({
      next: (mods) => {
        this.moderators = mods;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load moderators', err)
    });
  }

  viewModerator(mod: UserResponse): void {
    this.userService.selectUser(mod.id).subscribe({
      next: () => this.router.navigate(['/admin/profile']),
      error: (err) => console.error('Failed to select moderator', err)
    });
  }

}
