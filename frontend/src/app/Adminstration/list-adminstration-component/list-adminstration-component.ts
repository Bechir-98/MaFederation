import { RouterModule } from '@angular/router';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { AdministrationRepresentation } from '../../representations/Admin/adminstration-representation';
@Component({
  selector: 'app-list-adminstration-component',
  imports: [CommonModule],
  templateUrl: './list-adminstration-component.html',
  styleUrl: './list-adminstration-component.css'
})
export class ListAdminstrationComponent implements OnInit{


  clubId : number=1;
  Admins:Array<AdministrationRepresentation>=[];
  constructor(
    private clubservices: ClubServices,
    private cdr: ChangeDetectorRef
  ) {}

 ngOnInit(): void {
     this.clubservices.loadAdminstration(this.clubId).subscribe({
       next: (data: AdministrationRepresentation[]) => {
         this.Admins = data;
         console.log(data);
         this.cdr.detectChanges(); // Force view update if necessary
       },
       error: (err) => {
         console.error('Failed to load staff:', err);
       }
     });
   }
 }


