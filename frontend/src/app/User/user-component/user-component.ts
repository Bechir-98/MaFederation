import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
<<<<<<< HEAD
import { ClubMember } from '../../representations/ClubMember/ClubMember-representation';
=======
import { UserRepresentation } from '../../representations/User/user-representation';
>>>>>>> 4c8e664d1a3a0828495cc8346603a037aa807cce

@Component({
  selector: 'app-user-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-component.html',
  styleUrls: ['./user-component.css']
})
export class UserComponent {
  @Input() user!: ClubMember;

  get isPlayer(): boolean {
    return this.user?.type === 'PLAYER';
  }

  get isStaff(): boolean {
    return this.user?.type === 'STAFF';
  }

  get isAdmin(): boolean {
    return this.user?.type === 'ADMIN';
  }
}
