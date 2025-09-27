  import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
  import { CommonModule, NgForOf } from '@angular/common';
  import { Router, RouterLink } from '@angular/router';
  import { ModService } from '../../services/api/mod/mod.service';
  import { UserService } from '../../services/api/user/user-service';
  import { ResponceAdministration } from '../../representations/Admin/ResponceAdministration';

  @Component({
    selector: 'app-club-mods',
    standalone: true,
    imports: [CommonModule, NgForOf, RouterLink],
    templateUrl: './club-mods.component.html',
    styleUrls: ['./club-mods.component.css']
  })
  export class ClubModsComponent implements OnInit {
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
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Failed to load moderators', err)
      });
    }

    viewModerator(mod: ResponceAdministration): void {
      // ✅ Set current user ID via BehaviorSubject
      this.userService.setUserId(mod.id);
      this.router.navigate(['/clubadmin/profile']);
    }
  }
