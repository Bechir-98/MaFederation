import { Component, OnInit } from '@angular/core';
import { ClubMemberPost } from '../../representations/ClubMember/ClubMemberPost';
import { ClubServices } from '../../services/api/club/club-services';
import { COUNTRIES } from '../../representations/Countries';
import { ResponseClub } from '../../representations/Club/ResponseClub';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Category } from '../../representations/Category/category';
import { CategoryService } from '../../services/api/catergory/categories';
import { ClubMemberService } from '../../services/api/clubMember/club-member-service';

@Component({
  selector: 'app-add-member-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-member-component.html',
  styleUrl: './add-member-component.css',
})
export class AddMemberComponent implements OnInit {

  countries = COUNTRIES;
  categories: Category[] = [];

  clubs: ResponseClub[] = [];
  step = false;

  member: ClubMemberPost = {
    email: '',
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    phoneNumber: '',
    address: '',
    nationalID: '',
    nationality: '',
    type: 'PLAYER', 
    clubId: 0,
    passwordHash: '',
    categoryIds: []
  };

  constructor(
    private clubservice: ClubServices,
    private categoryService: CategoryService,
    private clubMemberService: ClubMemberService
  ) {}

  ngOnInit(): void {
    this.clubservice.loadClubs().subscribe({
      next: (res) => {
        this.clubs = res;
      },
    });
  }

  onCategoryChange(event: any): void {
  const categoryId = +event.target.value;

  if (event.target.checked) {
    if (!this.member.categoryIds.includes(categoryId)) {
      this.member.categoryIds.push(categoryId);
    }
  } else {
    this.member.categoryIds = this.member.categoryIds.filter(id => id !== categoryId);
  }
}

onClubChange(event: any): void {
  const clubId = +event.target.value;

  this.member.clubId = clubId;
  this.categoryService.getCategoriesByClubId(clubId).subscribe({
    next: (categories) => {
      this.categories = categories;
    },
    error: (err) => {
      console.error('Error fetching categories:', err);
    }
  });
}

  submitForm(): void {
    this.clubMemberService.addClubMember(this.member).subscribe({
      next: (response: ClubMemberPost) => {
        console.log('Member added successfully:', response);
      },
      error: () => {
        console.log(this.member)
        console.error('Error adding member:');

      }
   });
}



}