import { ResponceAudit } from "../Audit/ResponceAudit";



export interface ResponseClub extends ResponceAudit{
  name: string;
  location: string;
  foundedYear: number;
  contactEmail: string;
  contactPhone: string;
  bankAccount: string;
  bankName: string;
  website: string;
  categoryIds: number[];    
  memberIds: number[];
  logo: string;
  fileIds: number[]; 
}

export interface ClubFile extends ResponceAudit {
  type:  "LICENSE" | "REGISTRATION_CERTIFICATE" | "AFFILIATION_AGREEMENT" | "CLUB_OFFICIALS";
  content: string; 
}


