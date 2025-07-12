import { Component  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-list-adminstration-component',
  imports: [CommonModule],
  templateUrl: './list-adminstration-component.html',
  styleUrl: './list-adminstration-component.css'
})
export class ListAdminstrationComponent {

  Admins:Array<any>=[];

}
