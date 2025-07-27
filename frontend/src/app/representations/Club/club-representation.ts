


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