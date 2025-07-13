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
 role: 'PLAYER' | 'COACH' | 'ADMIN' |'Super';

}
