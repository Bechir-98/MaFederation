import { ClubMember } from '../ClubMember/ClubMember-representation';

export interface AdministrationRepresentation extends ClubMember {
  role: string;  // corresponds to the 'role' field in Administration entity
}
