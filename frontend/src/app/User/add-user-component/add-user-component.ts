import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';


@Component({
  selector: 'app-user-form',
  standalone: true,
  templateUrl: './add-user-component.html',
  styleUrls: ['./add-user-component.css'],
  imports: [CommonModule, FormsModule],
})
export class AddUserComponent {
 
}
