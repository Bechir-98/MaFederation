import { ResponceAudit } from "../Audit/ResponceAudit";

export interface UserResponse extends ResponceAudit{
  profilePicture?: string;   
  email?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
  phoneNumber?: string;
  address?: string;
  nationalID?: string;
  nationality?: string;
  type?: string;
  
}


export interface UserFile extends ResponceAudit{

  type:  'PASSPORT' | 'CV' | 'LICENSE' | 'OTHER';
  content: string; 

}

