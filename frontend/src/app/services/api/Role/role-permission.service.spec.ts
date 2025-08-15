import { TestBed } from '@angular/core/testing';

import { RolePermissionService } from './role-permission.service';

describe('RolePermissionService', () => {
  let service: RolePermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RolePermissionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
