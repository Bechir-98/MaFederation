import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-staff-component',
  imports: [FormsModule,CommonModule],
  templateUrl: './add-staff-component.html',
  styleUrl: './add-staff-component.css'
})
export class AddStaffComponent {
   step=1;

  nextStep() {
    this.step = 2;
  }

  onSubmit(){};


}
