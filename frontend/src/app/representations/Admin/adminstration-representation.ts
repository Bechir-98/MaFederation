import {  ClubMemberPost } from '../ClubMember/ClubMemberPost';

export interface AdministrationRepresentation extends ClubMemberPost {
  role: string;  // corresponds to the 'role' field in Administration entity
}
