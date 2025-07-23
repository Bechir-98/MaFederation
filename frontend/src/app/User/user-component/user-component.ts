import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRepresentation } from '../../representations/User/user-representation';

@Component({
  selector: 'app-user-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-component.html',
  styleUrls: ['./user-component.css']
})
export class UserComponent {
  @Input() user!: UserRepresentation; // <-- Ajout important
}
