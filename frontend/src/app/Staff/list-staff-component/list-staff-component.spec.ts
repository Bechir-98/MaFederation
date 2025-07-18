import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStaffComponent } from './list-staff-component';

describe('ListStaffComponent', () => {
  let component: ListStaffComponent;
  let fixture: ComponentFixture<ListStaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListStaffComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListStaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
