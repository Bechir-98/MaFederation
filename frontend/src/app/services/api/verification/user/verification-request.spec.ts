import { TestBed } from '@angular/core/testing';
import { VerificationRequestService } from './verification-request';



describe('VerificationRequest', () => {
  let service: VerificationRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VerificationRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
