import { MemberRepresentation } from './member-representation';
import { CategoryRepresentation } from './category-representation';

export interface StaffRepresentation extends MemberRepresentation {
  specialty: string;               // Staff specialty
  categories: CategoryRepresentation[];  // List of categories linked to staff
}
