import {ChangeDetectorRef, Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {ModDTO, ModService} from '../../services/api/mod/mod.service';

@Component({
  selector: 'app-club-mods',
  imports: [
    FormsModule,
    NgForOf,
    RouterLink
  ],
  templateUrl: './club-mods.component.html',
  styleUrl: './club-mods.component.css'
})
export class ClubModsComponent {
  moderators: ModDTO[] = [];
  filterFirstName = '';
  filterLastName = '';
  filterRole = '';

  constructor(
    private modService: ModService,
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

  viewModerator(mod: ModDTO): void {
    this.modService.selectModerator(mod.id).subscribe({
      next: () => this.router.navigate(['/admin/profile']),
      error: (err) => console.error('Failed to select moderator', err)
    });
  }


  filteredModerators(): ModDTO[] {
    return this.moderators.filter(mod =>
      mod.firstName.toLowerCase().includes(this.filterFirstName.toLowerCase()) &&
      mod.lastName.toLowerCase().includes(this.filterLastName.toLowerCase()) &&
      (this.filterRole ? mod.roles.some(r => r.name.includes(this.filterRole)) : true)
    );
  }

  getRolesString(mod: ModDTO): string {
    return mod.roles.map(r => r.name).join(', ');
  }


}
