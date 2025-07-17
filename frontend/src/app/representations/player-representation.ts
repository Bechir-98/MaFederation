import { MemberRepresentation } from './member-representation';
import { CategoryRepresentation } from './category-representation';

export interface PlayerRepresentation extends MemberRepresentation {
  jerseyNumber: number;
  height: number;
  weight: number;
  categories: CategoryRepresentation[];  // Liste des catégories associées
  licenseNumber:number,
}
