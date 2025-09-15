import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import  {ModsComponent} from '../mods-component/mods.component';
import {ClubModsComponent} from '../club-mods.component/club-mods.component';

@Component({
  selector: 'app-admin-type',
  standalone: true,
  imports: [CommonModule,ModsComponent,ClubModsComponent],
  templateUrl: './admin-type.component.html',
  styleUrls: ['./admin-type.component.css']
})
export class AdminTypeComponent {
  selectedType: 'club' | 'federation' | null = null;

  selectType(type: 'club' | 'federation') {
    this.selectedType = type;
  }
}
