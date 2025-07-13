import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-club-card-component',
  templateUrl: './club-card-component.html',
  styleUrls: ['./club-card-component.css'],
 
})
export class ClubCardComponent {
  @Input() clubName!: string;
  @Input() logoUrl!: string;
}
