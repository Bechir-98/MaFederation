import { MemberRepresentation } from './member-representation';

export interface AdministrationRepresentation extends MemberRepresentation {
  role: string;  // corresponds to the 'role' field in Administration entity
}
