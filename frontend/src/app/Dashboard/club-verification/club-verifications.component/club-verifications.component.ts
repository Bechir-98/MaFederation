import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClubVerificationRequestDTO, ClubVerificationRequestService } from '../../../services/api/verification/club/club-verification';
import { ClubServices } from '../../../services/api/club/club-services';

@Component({
  selector: 'app-club-verifications',
  standalone: true,
  templateUrl: './club-verifications.component.html',
  styleUrls: ['./club-verifications.component.css'],
  imports: [CommonModule, DatePipe, RouterModule, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ClubVerificationsComponent implements OnInit {
  requests: ClubVerificationRequestDTO[] = [];
  selectedType: 'ALL' | 'CLUB' = 'ALL';
  sortOrder: 'NEW' | 'OLD' = 'NEW';
  adminName = '';

  showRejectModal = false;
  currentRejectRequest: ClubVerificationRequestDTO | null = null;
  rejectReasons = ['Incomplete information', 'Invalid documents', 'Duplicate request', 'Other'];
  selectedRejectReason: string = '';
  rejectDescription: string = '';

  constructor(
    private service: ClubVerificationRequestService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.service.getPending().subscribe({
      next: (data) => {
        this.requests = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load requests:', err)
    });
  }

  // Just navigate to club profile; backend uses JWT to know clubId
  selectClub(clubId: number) {
    this.router.navigate(['/admin/club/profile']);
  }

  approve(req: ClubVerificationRequestDTO) {
    const adminName = 'Admin1';
    this.service.approve(req.id, adminName).subscribe(() => this.loadRequests());
  }

  reject(req: ClubVerificationRequestDTO) {
    const reason = prompt('Reason for rejection:') || '';
    const adminName = 'Admin1';
    this.service.reject(req.id, adminName, reason).subscribe(() => this.loadRequests());
  }

  get filteredRequests(): ClubVerificationRequestDTO[] {
    let result = [...this.requests];
    result.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortOrder === 'NEW' ? dateB - dateA : dateA - dateB;
    });
    return result;
  }

  openRejectModal(request: ClubVerificationRequestDTO) {
    this.currentRejectRequest = request;
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
}
