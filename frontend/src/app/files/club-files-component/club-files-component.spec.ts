import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubFiles } from './club-files-component';

describe('FileCardComponent', () => {
  let component: ClubFiles;
  let fixture: ComponentFixture<ClubFiles>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubFiles]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubFiles);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
