import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { ClubMemberService } from '../../services/api/clubMember/club-member-service';
import { ClubMemberResponse } from '../../representations/ClubMember/ClubMemberResponce';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-club-member',
  templateUrl: './club-member-component.html',
  styleUrls: ['./club-member-component.css'],
  standalone: true,
  imports: [DatePipe, CommonModule],
})
export class ClubMemberComponent implements OnInit {
  memberId: number = 0;
  clubId: number = 0;
  public member?: ClubMemberResponse;

  constructor(
    private clubMemberService: ClubMemberService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef // 👈 Inject ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const clubId = params.get('clubId');
      const memberId = params.get('memberId');
      if (clubId && memberId) {
        this.clubId = +clubId;
        this.memberId = +memberId;
        this.loadMember(this.memberId, this.clubId);
      }
    });
  }

  public loadMember(memberId: number, clubId: number) {
    this.clubMemberService.getMemberById(memberId, clubId).subscribe({
      next: (memberData: ClubMemberResponse) => {
        this.member = memberData;

        // 👇 Trigger change detection manually
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Failed to load member', err);
      }
    });
  }
}
