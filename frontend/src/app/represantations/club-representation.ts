export interface ClubRepresentation {
  clubId: number;
  name: string;
  location: string;
  foundedYear: number;
  contactEmail: string;
  contactPhone: string;
  bankAccount: string;
  bankName: string;
  logoFileId: number;
  isValidatedByFederation: boolean;
  validatedAt: Date | null;
  validatedByUserId: number | null;
  rejectionReason?: string | null;
  logoUrl:string;
}
