import { MemberRepresentation } from '../ClubMember/ClubMember-representation';
import { CategoryRepresentation } from '../Category/category-representation';

export interface StaffRepresentation extends MemberRepresentation {
  specialty: string;               // Staff specialty
  categories: CategoryRepresentation[];  // List of categories linked to staff
}
