import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-list-staff-component',
  imports: [CommonModule,RouterModule],
  templateUrl: './list-staff-component.html',
  styleUrl: './list-staff-component.css'
})
export class ListStaffComponent {

  Staffs:Array<any>=[];

}
