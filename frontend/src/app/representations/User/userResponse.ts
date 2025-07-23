export interface UserResponse {
  userId: number;

  email: string;

  firstName: string;

  lastName: string;

  dateOfBirth: string; // Format: YYYY-MM-DD

  gender: string;

  phoneNumber: string;

  address: string;

  nationalID: string;

  nationality: string;


  fileIds: number[]; // correspond à List<Integer> fileIds en Java
}


