import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddAdministrationComponent } from './add-administration-component';



describe('AddAdminstrationComponent', () => {
  let component: AddAdministrationComponent;
  let fixture: ComponentFixture<AddAdministrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAdministrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
