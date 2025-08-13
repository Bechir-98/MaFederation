import { TestBed } from '@angular/core/testing';

import { StaffServices } from './staff-services';

describe('StaffServices', () => {
  let service: StaffServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StaffServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
