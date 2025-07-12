import { TestBed } from '@angular/core/testing';

import { ClubServices } from './club-services';

describe('ClubServices', () => {
  let service: ClubServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClubServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
