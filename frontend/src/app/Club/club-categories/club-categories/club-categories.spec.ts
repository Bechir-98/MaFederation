import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubCategories } from './club-categories';

describe('ClubCategories', () => {
  let component: ClubCategories;
  let fixture: ComponentFixture<ClubCategories>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubCategories]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubCategories);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
