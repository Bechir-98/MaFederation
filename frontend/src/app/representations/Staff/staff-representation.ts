import { ClubMember } from '../ClubMember/ClubMember-representation';
import { CategoryRepresentation } from '../Category/category-representation';

export interface StaffRepresentation extends ClubMember {
  specialty: string;               // Staff specialty
  categories: CategoryRepresentation[];  // List of categories linked to staff
}
