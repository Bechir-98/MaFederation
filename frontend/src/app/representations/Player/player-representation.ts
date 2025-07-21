import { MemberRepresentation } from '../ClubMember/ClubMember-representation';
import { CategoryRepresentation } from '../Category/category-representation';

export interface PlayerRepresentation extends MemberRepresentation {
  jerseyNumber: number;
  height: number;
  weight: number;
  categories: CategoryRepresentation[];  // Liste des catégories associées
  licenseNumber:number,
}
