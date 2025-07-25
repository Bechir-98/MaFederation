<<<<<<< HEAD
import { ClubMember } from '../ClubMember/ClubMember-representation';
=======
import { MemberRepresentation } from '../ClubMember/ClubMember-representation';
>>>>>>> 4c8e664d1a3a0828495cc8346603a037aa807cce
import { CategoryRepresentation } from '../Category/category-representation';

export interface StaffRepresentation extends ClubMember {
  specialty: string;               // Staff specialty
  categories: CategoryRepresentation[];  // List of categories linked to staff
}
