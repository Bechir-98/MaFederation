import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerAddComponent } from './player-add-component';

describe('PlayerAddComponent', () => {
  let component: PlayerAddComponent;
  let fixture: ComponentFixture<PlayerAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayerAddComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayerAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
