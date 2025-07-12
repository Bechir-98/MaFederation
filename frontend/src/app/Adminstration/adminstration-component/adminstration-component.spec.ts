import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminstrationComponent } from './adminstration-component';

describe('AdminstrationComponent', () => {
  let component: AdminstrationComponent;
  let fixture: ComponentFixture<AdminstrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminstrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminstrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
