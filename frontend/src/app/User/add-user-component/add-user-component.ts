import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { COUNTRIES } from '../../representations/Countries';
<<<<<<< HEAD

=======
import { UserRepresentation } from '../../representations/User/user-representation';
>>>>>>> 4c8e664d1a3a0828495cc8346603a037aa807cce

@Component({
  selector: 'app-user-form',
  standalone: true,
  templateUrl: './add-user-component.html',
  styleUrls: ['./add-user-component.css'],
  imports: [CommonModule, FormsModule],
})
export class AddUserComponent {
 
}
