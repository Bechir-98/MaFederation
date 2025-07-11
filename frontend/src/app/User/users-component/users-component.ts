import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users-component',
  imports: [CommonModule],
  templateUrl: './users-component.html',
  styleUrl: './users-component.css'
})
export class UsersComponent {

  Users: Array<any > =[];

}
