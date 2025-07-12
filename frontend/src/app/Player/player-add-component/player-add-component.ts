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

  onSubmit() {
    alert('Form submitted successfully!');
  }
}
