import { ClubMemberPost } from '../ClubMember/ClubMemberPost';
import { Category } from '../Category/category';

export interface PlayerRepresentation extends ClubMemberPost {

  
  jerseyNumber: number;
  height: number;
  weight: number;
  categories: Category[];  // Liste des catégories associées
  licenseNumber:number,


}
