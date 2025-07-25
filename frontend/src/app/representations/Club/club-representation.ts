import { CategoryRepresentation } from "../Category/category-representation";
<<<<<<< HEAD

=======
import { MemberRepresentation } from "../ClubMember/ClubMember-representation";
>>>>>>> 4c8e664d1a3a0828495cc8346603a037aa807cce


export interface ClubRepresentation {
  clubId?: number;
  name: string;
  location: string;
  foundedYear: number;
  contactEmail: string;
  contactPhone: string;
  bankAccount: string;
  bankName: string;
  categoryIds: number[];           // match backend: list of category IDs only
  memberIds: number[];
  files: ClubFile;
}

export interface ClubFile {
  id: number;
  licenseUrl: string;
  logoUrl: string;
}