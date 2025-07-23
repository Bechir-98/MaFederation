import { ClubMember } from '../ClubMember/ClubMember-representation';
import { CategoryRepresentation } from '../Category/category-representation';

export interface PlayerRepresentation extends ClubMember {

  
  jerseyNumber: number;
  height: number;
  weight: number;
  categories: CategoryRepresentation[];  // Liste des catégories associées
  licenseNumber:number,




}
