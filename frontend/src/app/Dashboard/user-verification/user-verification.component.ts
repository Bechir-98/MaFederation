import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { UserType, VerificationRequestDTO, VerificationRequestService } from '../../services/api/verification/user/verification-request';
import { DatePipe, CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/api/user/user-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-verifications',
  standalone: true,
  templateUrl: './user-verification.component.html',
  styleUrls: ['./user-verification.component.css'],
  imports: [DatePipe, CommonModule, RouterModule, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserVerificationsComponent implements OnInit {
  requests: VerificationRequestDTO[] = [];
  selectedType: UserType | 'ALL' = 'ALL';
  sortOrder: 'NEW' | 'OLD' = 'NEW';
  adminName = 'Admin1'; 

  showRejectModal = false;
  currentRejectRequest: VerificationRequestDTO | null = null;
  rejectReasons = [
    'Incomplete information',
    'Invalid documents',
    'Duplicate request',
    'Other'
  ];
  selectedRejectReason: string = '';
  rejectDescription: string = '';

  constructor(
    private service: VerificationRequestService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.service.getPending().subscribe(data => {
      this.requests = data;
      console.log(data)
      this.cdr.markForCheck();
    });
  }

  approve(req: VerificationRequestDTO) {
    this.service.approve(req.id, this.adminName).subscribe(() => this.loadRequests());
  }

  openRejectModal(req: VerificationRequestDTO) {
    this.currentRejectRequest = req;
    this.selectedRejectReason = this.rejectReasons[0];
    this.rejectDescription = '';
    this.showRejectModal = true;
  }

  closeRejectModal() {
    this.showRejectModal = false;
    this.currentRejectRequest = null;
  }

  confirmReject() {
    if (!this.currentRejectRequest) return;

    const reason = this.selectedRejectReason + (this.rejectDescription ? `: ${this.rejectDescription}` : '');
    this.service.reject(this.currentRejectRequest.id, this.adminName, reason)
      .subscribe({
        next: () => {
          alert('Request rejected successfully!');
          this.closeRejectModal();
          this.loadRequests();
        },
        error: (err) => {
          console.error('Failed to reject request:', err);
          alert('Failed to reject request.');
        }
      });
  }

  viewProfile(userId: number, type: UserType) {
    this.userService.selectUser(userId).subscribe({
      next: () => {
        let route = '';
        switch (type) {
          case 'PLAYER': route = '/club/players/profile'; break;
          case 'STAFF': route = '/club/staff/profile'; break;
          case 'ADMIN': route = '/club/admins/profile'; break;
        }
        this.router.navigate([route]);
      },
      error: err => console.error('Failed to select user', err)
    });
  }

  get filteredRequests(): VerificationRequestDTO[] {
    let result = [...this.requests];
    if (this.selectedType !== 'ALL') {
      result = result.filter(r => r.targetType === this.selectedType);
    }
    result.sort((a, b) => new Date(this.sortOrder === 'NEW' ? b.createdAt : a.createdAt).getTime() - new Date(this.sortOrder === 'NEW' ? a.createdAt : b.createdAt).getTime());
    return result;
  }
}
