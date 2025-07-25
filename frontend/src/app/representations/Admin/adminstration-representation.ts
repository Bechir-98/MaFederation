<<<<<<< HEAD
import { ClubMember } from '../ClubMember/ClubMember-representation';
=======
import { MemberRepresentation } from '../ClubMember/ClubMember-representation';
>>>>>>> 4c8e664d1a3a0828495cc8346603a037aa807cce

export interface AdministrationRepresentation extends ClubMember {
  role: string;  // corresponds to the 'role' field in Administration entity
}
