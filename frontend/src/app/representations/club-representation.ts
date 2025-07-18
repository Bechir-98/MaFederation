import { CategoryRepresentation } from "./category-representation";
import { MemberRepresentation } from "./member-representation";


export interface ClubRepresentation {
  clubId?: number;
  name: string;
  location: string;
  foundedYear: number;
  contactEmail: string;
  contactPhone: string;
  bankAccount: string;
  bankName: string;
  categories: CategoryRepresentation[];      
  members: MemberRepresentation[];          
  files: ClubFile ; 
}



export interface ClubFile {
  id: number;
  licenseUrl: string;
  logoUrl: string;

}