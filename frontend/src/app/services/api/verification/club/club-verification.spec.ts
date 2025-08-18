import { TestBed } from '@angular/core/testing';
import { ClubVerificationRequestService } from './club-verification';



describe('Club', () => {
  let service: ClubVerificationRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClubVerificationRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
