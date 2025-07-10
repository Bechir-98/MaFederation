import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { BrowserModule } from '@angular/platform-browser';
@Component({
  selector: 'app-player-add-component',
  imports: [FormsModule,BrowserModule],
  templateUrl: './player-add-component.html',
  styleUrl: './player-add-component.css'
})
export class PlayerAddComponent {

  categories:Array<any>=[];
  photoPreview:boolean=false;

  onSubmit(){}

  onPhotoSelected(){}

}
