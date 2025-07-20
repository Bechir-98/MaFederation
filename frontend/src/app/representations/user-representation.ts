export interface UserRepresentation {
  userId: number;
  firstName: string;
  lastName: string;
  email: string;
  dateOfBirth: string; // Format: YYYY-MM-DD;
  phoneNumber: string;
  nationality: string;
  address: string;
  gender: string;
  country: string;
  nationalID?: string;
}
