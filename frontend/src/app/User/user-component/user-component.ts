import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClubMemberPost } from '../../representations/ClubMember/ClubMemberPost';

@Component({
  selector: 'app-user-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-component.html',
  styleUrls: ['./user-component.css']
})
export class UserComponent {
  @Input() user!: ClubMemberPost;

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
