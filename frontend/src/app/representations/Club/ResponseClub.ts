


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
  files: ClubFile;
}

export interface ClubFile {
  id: number;
  licenseUrl: string;
  logoUrl: string;
}