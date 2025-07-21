export interface UserRepresentation {
  userId: number;
  email: string;
  firstName: string;
  lastName: string;
  dateOfBirth: string; // Format: YYYY-MM-DD
  gender: string;
  phoneNumber: string;
  address: string;
  nationalID?: string;
  nationality: string;
  country: string;
  fileIds: number[]; // correspond à List<Integer> fileIds en Java
}


