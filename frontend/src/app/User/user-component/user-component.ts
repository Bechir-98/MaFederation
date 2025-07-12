import { Component, Input, OnInit ,} from '@angular/core';
import { UserRepresentation } from '../../representations/user-representation';
import { UserService } from '../.././services/api/user/user-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-component.html',
  standalone:true,
  styleUrls: ['./user-component.css']
})
export class UserComponent implements OnInit {
  @Input() user!: UserRepresentation;
  @Input() profileImageUrl: string = 'assets/images/default-profile.jpg';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    // Initialize component logic here
  }

  getStatusClass(): string {
    switch (this.user.status.toLowerCase()) {
      case 'active':
        return 'validated';
      case 'pending':
        return 'pending';
      case 'rejected':
        return 'rejected';
      default:
        return '';
    }
  }

  getStatusDisplay(): string {
    switch (this.user.status.toLowerCase()) {
      case 'active':
        return '✅ Validated';
      case 'pending':
        return '⏳ Pending';
      case 'rejected':
        return '❌ Rejected';
      default:
        return this.user.status;
    }
  }

  getStatusTitle(): string {
    switch (this.user.status.toLowerCase()) {
      case 'active':
        return 'Validated by federation';
      case 'pending':
        return 'Pending validation';
      case 'rejected':
        return 'Rejected by federation';
      default:
        return this.user.status;
    }
  }

  onEdit(): void {
    // Implement edit functionality
    console.log('Edit user:', this.user);
  }

  onMoreDetails(): void {
    console.log('Show more details for user:', this.user);
  }

  onViewAccess(): void {
    // Implement view access functionality
    console.log('View access for user:', this.user);
  }

  onFederationInfo(): void {
    // Implement federation info functionality
    console.log('Show federation info for user:', this.user);
  }
}