import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ModDTO, ModService } from '../../services/api/mod/mod.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-mods-component',
  standalone: true,
  imports: [CommonModule, RouterModule,FormsModule],
  templateUrl: './mods.component.html',
  styleUrls: ['./mods.component.css']
})
export class ModsComponent implements OnInit {

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
