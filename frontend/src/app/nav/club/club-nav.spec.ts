import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubNav } from './club-nav';

describe('Nav', () => {
  let component: ClubNav;
  let fixture: ComponentFixture<ClubNav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubNav]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubNav);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
