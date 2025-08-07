import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAdminstrationComponent } from './add-adminstration-component';

describe('AddAdminstrationComponent', () => {
  let component: AddAdminstrationComponent;
  let fixture: ComponentFixture<AddAdminstrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAdminstrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAdminstrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
