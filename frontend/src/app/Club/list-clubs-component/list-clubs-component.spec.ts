import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListClubsComponent } from './list-clubs-component';

describe('ListClubsComponent', () => {
  let component: ListClubsComponent;
  let fixture: ComponentFixture<ListClubsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListClubsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListClubsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
