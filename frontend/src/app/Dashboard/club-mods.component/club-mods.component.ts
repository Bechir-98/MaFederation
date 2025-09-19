import { ChangeDetectorRef, Component } from '@angular/core';
import { NgForOf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ModService } from '../../services/api/mod/mod.service';
import { UserService } from '../../services/api/user/user-service';
import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';

@Component({
  selector: 'app-club-mods',
  imports: [
    NgForOf,
    RouterLink
  ],
  templateUrl: './club-mods.component.html',
  styleUrl: './club-mods.component.css'
})
export class ClubModsComponent {
  moderators: ResponceAdministration[] = [];

  constructor(
    private userService: UserService,
    private modService: ModService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadModerators();
  }

  loadModerators(): void {
    this.modService.getAllClubAdmins().subscribe({
      next: (mods) => {
        this.moderators = mods;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load moderators', err)
    });
  }

  viewModerator(mod: ResponceAdministration): void {
    this.userService.selectUser(mod.id).subscribe({
      next: () => this.router.navigate(['/clubadmin/profile']),
      error: (err) => console.error('Failed to select moderator', err)
    });
  }
}
