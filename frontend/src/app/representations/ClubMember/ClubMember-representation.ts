
import { UserPost } from "../User/userPost"

export interface ClubMember extends UserPost {
    clubId?: number; 
    type: string;
    password?: string; // Optional password field

}

