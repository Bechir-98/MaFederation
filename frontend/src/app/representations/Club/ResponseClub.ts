


export interface ResponseClub{
  clubId: number;
  name: string;
  location: string;
  foundedYear: number;
  contactEmail: string;
  contactPhone: string;
  bankAccount: string;
  bankName: string;
  categoryIds: number[];    
  memberIds: number[];
  logo: string;
  fileIds: number[]; 
}

export interface ClubFile {
  id: number;
  type:  "LICENSE" | "REGISTRATION_CERTIFICATE" | "AFFILIATION_AGREEMENT" | "CLUB_OFFICIALS";
  content: string; 
}


