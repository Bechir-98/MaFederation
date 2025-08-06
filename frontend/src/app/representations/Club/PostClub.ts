import { PostAudit } from "../Audit/PostAudit";


export interface PostClub extends PostAudit{
name: string;
  location: string;
  foundedYear: number;
  contactEmail: string;
  contactPhone: string;
  bankAccount: string;
  bankName: string;
  categoryIds: number[]; 
  logo: File|null; 
}