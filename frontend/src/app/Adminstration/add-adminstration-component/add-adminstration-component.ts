import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-adminstration-component',
  imports: [FormsModule,CommonModule],
  templateUrl: './add-adminstration-component.html',
  styleUrl: './add-adminstration-component.css'
})
export class AddAdminstrationComponent {
  step=1;

  nextStep() {
    this.step = 2;
  }

  onSubmit(){};

}
