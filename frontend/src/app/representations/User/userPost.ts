import { PostAudit } from "../Audit/PostAudit";


export interface UserPost extends PostAudit{
  email: string;
  profilePicture: File|null; 
  passwordHash: string;
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  gender: string;
  phoneNumber: string;
  address: string;
  nationalID: string;
  nationality: string;

}



