import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-player-add-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './player-add-component.html',
  styleUrls: ['./player-add-component.css']
})
export class PlayerAddComponent {
  step = 1;

  categories: string[] = ['U15', 'U17', 'U20', 'Senior'];
  photoPreview: string | null = null;

  filePreviews = {
    medical: null as string | null,
    cin: null as string | null,
    birth: null as string | null
  };

  fileTypes = {
    medical: '' as 'pdf' | 'image' | '',
    cin: '' as 'pdf' | 'image' | '',
    birth: '' as 'pdf' | 'image' | ''
  };

  nextStep() {
    this.step = 2;
  }

  onPhotoSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      this.photoPreview = reader.result as string;
    };
    reader.readAsDataURL(file);
  }

  onFileSelected(event: Event, type: 'medical' | 'cin' | 'birth') {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      this.filePreviews[type] = reader.result as string;
      this.fileTypes[type] = file.type.includes('pdf') ? 'pdf' : 'image';
    };
    reader.readAsDataURL(file);
  }

  onSubmit() {
    alert('Form submitted successfully!');
  }
}
