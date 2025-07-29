


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

// export interface ClubFile {
//   id: number;
//   type: 'PROFILE_PICTURE' | 'PASSPORT' | 'CV' | 'LICENSE' | 'OTHER';
//   content: string; // Base64 encoded string (if coming from JSON)
// }
