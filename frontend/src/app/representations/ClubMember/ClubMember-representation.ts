<<<<<<< HEAD
import { UserPost } from "../User/userPost"
=======
import { UserRepresentation } from "../User/user-representation";
>>>>>>> 4c8e664d1a3a0828495cc8346603a037aa807cce

export interface ClubMember extends UserPost {
    clubId?: number; 
    type: string;
    password?: string; // Optional password field

}

