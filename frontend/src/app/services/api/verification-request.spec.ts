import { TestBed } from '@angular/core/testing';

import { VerificationRequest } from './verification-request';

describe('VerificationRequest', () => {
  let service: VerificationRequest;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VerificationRequest);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
