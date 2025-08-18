import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAdministrationComponent } from './list-adminstration-component';

describe('ListAdminstrationComponent', () => {
  let component: ListAdministrationComponent;
  let fixture: ComponentFixture<ListAdministrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAdministrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
