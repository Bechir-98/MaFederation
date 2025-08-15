import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserVerificationsComponent } from './user-verification.component';



describe('UserVerification', () => {
  let component: UserVerificationsComponent;
  let fixture: ComponentFixture<UserVerificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserVerificationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserVerificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
