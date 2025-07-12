import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-users-component',
  imports: [CommonModule,RouterModule],
  templateUrl: './users-component.html',
  styleUrl: './users-component.css'
})
export class UsersComponent {
  
  Users: Array<any > =[];

}
