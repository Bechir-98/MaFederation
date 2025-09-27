import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddClubAdminComponent } from './add-club-mod';

describe('AddClubMod', () => {
  let component: AddClubAdminComponent;
  let fixture: ComponentFixture<AddClubAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddClubAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddClubAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
