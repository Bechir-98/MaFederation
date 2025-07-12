export interface UserRepresentation {
  userID: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  address: string;
  city: string;
  postalCode: string;
  nationality:string;
  gender: string
  dateOfBirth: string; // Format: YYYY-MM-DD
  country: string;
  role: string; // e.g., "PLAYER", "COACH", "ADMIN"
  status: string; // e.g., "PENDING", "ACTIVE", "REJECTED"
  rejectionReason?: string; // Optional, only if status is REJECTED
 
}
