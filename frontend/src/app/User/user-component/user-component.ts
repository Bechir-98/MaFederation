import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-component',
  templateUrl: './user-component.html',
  styleUrls: ['./user-component.css']
})
export class UserComponent {
  @Input() user!: any;  // Modèle générique avec tous les champs

  get profileImageUrl(): string {
    // Retourne l'URL de la photo de profil ou une image par défaut
    return this.user?.profilePhotoUrl || 'assets/default-profile.png';
  }

  getStatusTitle(): string {
    return this.user?.status || 'No status available';
  }

  getStatusDisplay(): string {
    // Tu peux personnaliser l'affichage du statut
    if (!this.user?.status) return 'Unknown';
    return this.user.status;
  }

  onEdit() {
    console.log('Edit clicked');
    // Logique pour éditer l'utilisateur
  }

  onMoreDetails() {
    console.log('More Details clicked');
    // Logique pour afficher plus de détails
  }

  onViewAccess() {
    console.log('View Access clicked');
    // Logique pour afficher les droits d'accès
  }

  onFederationInfo() {
    console.log('Federation Info clicked');
    // Logique pour afficher infos fédération
  }

  isPlayer(): boolean {
    return this.user?.role === 'player';
  }

  isStaff(): boolean {
    return this.user?.role === 'staff';
  }

  isAdmin(): boolean {
    return this.user?.role === 'office';
  }
}
