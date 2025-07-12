import { Component ,Input} from '@angular/core';

@Component({
  selector: 'app-club-card-component',
  imports: [],
  templateUrl: './club-card-component.html',
  styleUrl: './club-card-component.css'
})
export class ClubCardComponent {



  @Input() clubName!:string;
  
  @Input() logoUrl!:string;


}
