import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubModsComponent } from './club-mods.component';

describe('ClubModsComponent', () => {
  let component: ClubModsComponent;
  let fixture: ComponentFixture<ClubModsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubModsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubModsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
