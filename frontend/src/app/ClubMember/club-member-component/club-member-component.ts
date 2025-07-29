import { Component, OnInit, Input,} from '@angular/core';
import { ClubMemberService } from '../../services/api/clubMember/club-member-service';
import { ClubMemberResponse } from '../../representations/ClubMember/ClubMemberResponce';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-club-member',
  templateUrl: './club-member-component.html',
  styleUrls: ['./club-member-component.css'],
  imports: [DatePipe,CommonModule],
})
export class ClubMemberComponent implements OnInit {
  
  @Input() memberId!: number;

  public member?: ClubMemberResponse;

  constructor(private clubMemberService: ClubMemberService) {}

  ngOnInit(): void {
    if (this.memberId) {
      this.loadMember(this.memberId);
    }
  }

  loadMember(id: number): void {
    this.clubMemberService.getMemberById(id).subscribe({
      next: (memberData) => {
        this.member = memberData;
      },
      error: (err) => {
        console.error('Failed to load member', err);
      }
    });
  }
}
