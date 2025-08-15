import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { VerificationRequestDTO, VerificationRequestService } from '../../services/api/verification-request';
import { DatePipe, CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-verifications',
  standalone: true, // ✅ Needed for `imports` in @Component
  templateUrl: './user-verification.component.html',
  styleUrls: ['./user-verification.component.css'],
  imports: [DatePipe, CommonModule, RouterModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserVerificationsComponent implements OnInit {
  requests: VerificationRequestDTO[] = [];

  constructor(
    private service: VerificationRequestService,
    private cdr: ChangeDetectorRef,
    private router: Router // ✅ Inject router
  ) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests() {
    this.service.getPending().subscribe(data => {
      this.requests = data;
      console.log(data);
      this.cdr.markForCheck(); // ✅ trigger update
    });
  }

  approve(req: VerificationRequestDTO) {
    const adminName = 'Admin1'; // should come from session
    this.service.approve(req.id, adminName).subscribe(() => {
      this.loadRequests();
    });
  }

  reject(req: VerificationRequestDTO) {
    const reason = prompt('Reason for rejection:') || '';
    const adminName = 'Admin1';
    this.service.reject(req.id, adminName, reason).subscribe(() => {
      this.loadRequests();
    });
  }

  viewProfile(userId: number) {
    // Navigate to profile page
    this.router.navigate(['/users', userId]);
  }
}
