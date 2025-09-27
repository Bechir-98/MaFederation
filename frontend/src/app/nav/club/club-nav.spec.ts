import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubNavComponent } from './club-nav';

describe('Nav', () => {
  let component: ClubNavComponent;
  let fixture: ComponentFixture<ClubNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubNavComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
