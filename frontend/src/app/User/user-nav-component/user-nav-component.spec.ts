import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserNavComponent } from './user-nav-component';

describe('UserComponent', () => {
  let component: UserNavComponent;
  let fixture: ComponentFixture<UserNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserNavComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
