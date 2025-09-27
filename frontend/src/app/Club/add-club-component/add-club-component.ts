import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { Category } from '../../representations/Category/category';
import { CategoryService } from '../../services/api/catergory/categories';
import { PostClub } from '../../representations/Club/PostClub';
import { Router } from '@angular/router';

@Component({
  selector: 'app-club-add-component',
  templateUrl: './add-club-component.html',
  standalone: true,
  styleUrls: ['./add-club-component.css'],
  imports: [FormsModule, CommonModule],
})
export class AddClubComponent implements OnInit {
  categories: Category[] = [];
  club: PostClub = {
    name: '',
    location: '',
    foundedYear: new Date().getFullYear(),
    contactEmail: '',
    contactPhone: '',
    bankAccount: '',
    bankName: '',
    categoryIds: [],
    logo: null,
    website: ''
  };

  logoPreview: string | ArrayBuffer | null = null;
  logoInvalid = false;

  constructor(
    private clubservice: ClubServices,
    private categoryService: CategoryService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.categoryService.loadAllCategories().subscribe({
      next: (response) => {
        this.categories = response;
        this.cd.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  onLogoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      if (!file.type.startsWith('image/')) {
        this.logoInvalid = true;
        this.logoPreview = null;
        this.club.logo = null;
        this.cd.detectChanges();
        return;
      }
      this.club.logo = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.logoPreview = reader.result;
        this.logoInvalid = false;
        this.cd.detectChanges();
      };
      reader.readAsDataURL(file);
    } else {
      this.logoInvalid = true;
      this.logoPreview = null;
      this.club.logo = null;
      this.cd.detectChanges();
    }
  }

  onSubmit(): void {
    if (this.logoInvalid || !this.club.logo) {
      alert('Veuillez sélectionner un logo valide avant de soumettre.');
      return;
    }

    const { logo, ...clubWithoutLogo } = this.club;
    const formData = new FormData();
    const clubJsonBlob = new Blob([JSON.stringify(clubWithoutLogo)], { type: 'application/json' });
    formData.append('club', clubJsonBlob);

    if (logo) formData.append('logo', logo);

    this.clubservice.createClub(formData).subscribe({
      next: (createdClub) => {
        alert('✅ Club registered successfully!');
        // Navigate to club profile; backend determines clubId from JWT
        this.router.navigate(['/admin/club/profile']);
      },
      error: (err) => {
        console.error('❌ Error creating club:', err);
        alert('❌ Failed to register club.');
      }
    });
  }

  onCategoryChange(category: Category, event: any) {
    if (event.target.checked) {
      if (!this.club.categoryIds.includes(category.id!)) {
        this.club.categoryIds.push(category.id!);
      }
    } else {
      this.club.categoryIds = this.club.categoryIds.filter(id => id !== category.id);
    }
    this.cd.detectChanges();
  }
}
