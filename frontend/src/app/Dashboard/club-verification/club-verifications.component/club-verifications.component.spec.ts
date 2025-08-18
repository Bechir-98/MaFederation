import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubVerificationsComponent } from './club-verifications.component';

describe('ClubVerificationsComponent', () => {
  let component: ClubVerificationsComponent;
  let fixture: ComponentFixture<ClubVerificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubVerificationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubVerificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
