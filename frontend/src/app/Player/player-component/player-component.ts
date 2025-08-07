import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PlayerService } from '../../services/api/player/player-service';
import { PlayerResponce } from '../../representations/Player/playerResponce';
import { CommonModule } from '@angular/common';
import { UserFilesComponent } from '../../files/user-files-component/user-files-component';

@Component({
  selector: 'app-player',
  standalone: true,
  imports: [CommonModule,UserFilesComponent],
  templateUrl: './player-component.html',
  styleUrls: ['./player-component.css']
})
export class PlayerComponent implements OnInit {
  player!: PlayerResponce;
  activeSection: string = 'basic';
  loading = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private playerService: PlayerService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const playerId = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(playerId)) {
      this.error = 'Invalid player ID';
      this.loading = false;
      this.cdr.detectChanges();
      return;
    }

    this.playerService.getPlayerById(playerId).subscribe({
      next: (data) => {
        this.player = data;
        this.loading = false;
        console.log(data);
        this.cdr.detectChanges();  // Trigger change detection after data loads
      },
      error: () => {
        this.error = 'Failed to load player data';
        this.loading = false;
        this.cdr.detectChanges();  // Trigger change detection on error
      }
    });
  }

  showSection(section: string) {
    this.activeSection = section;
    this.cdr.detectChanges();  // Update UI immediately after changing section
  }
  
}
