import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAdminstrationComponent } from './list-adminstration-component';

describe('ListAdminstrationComponent', () => {
  let component: ListAdminstrationComponent;
  let fixture: ComponentFixture<ListAdminstrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAdminstrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAdminstrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
