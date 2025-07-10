import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerMedicalComponent } from './player-medical-component';

describe('PlayerMedicalComponent', () => {
  let component: PlayerMedicalComponent;
  let fixture: ComponentFixture<PlayerMedicalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayerMedicalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayerMedicalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
