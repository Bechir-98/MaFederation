import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileCardComponent } from './club-files-component';

describe('FileCardComponent', () => {
  let component: FileCardComponent;
  let fixture: ComponentFixture<FileCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FileCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
