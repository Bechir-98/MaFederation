import { TestBed } from '@angular/core/testing';

import { ClubMemberService } from './club-member-service';

describe('ClubMemberService', () => {
  let service: ClubMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClubMemberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
